package com.idt.aio.service;

import com.idt.aio.config.RabbitMqConfig;
import com.idt.aio.entity.constant.State;
import com.idt.aio.repository.DocumentRepository;
import com.idt.aio.request.DocumentStatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentStatusService {
    private final Map<String, SseEmitter> emitters;
    private final DocumentRepository documentRepository;

    @Transactional
    @RabbitListener(queues = RabbitMqConfig.FILE_CONTENT_RESULT_QUEUE)
    public void sendStateUpdate(final DocumentStatusResponse response) {
        try {
            long currentTimestamp = System.currentTimeMillis();
            if (response.timestamp() > currentTimestamp) {
                // 메시지가 지연된 경우 처리 로직 (시스템 간 시계 차이, 예정된 메시지)
                return;
            }

            documentRepository.updateStats(response.docId(), response.state());
            SseEmitter emitter = emitters.get(response.jobId());
            if (emitter != null) {
                sendUpdate(emitter, response);
            }
        } catch (Exception e) {
            // 재시도 로직 및 오류 처리
            handleRetry(response, e);
        }
    }

    private void sendUpdate(SseEmitter emitter, DocumentStatusResponse response) throws Exception {
        // 클라이언트에 상태 업데이트 전송
        emitter.send(SseEmitter.event()
                .name(String.format("document-%s, Status Update", response.docId()))
                .data(response.state()));
        // 상태가 "serving"이면 SSE 연결 종료
        if (State.SERVING.equals(response.state())) {
            emitter.complete();
            emitters.remove(response.jobId());
        }
    }

    private void handleRetry(DocumentStatusResponse response, Exception e) {
        int maxRetries = 3;
        int retryCount = 0;
        // 재시도 시에도 동일한 emitter를 사용하도록 미리 가져옵니다.
        SseEmitter emitter = emitters.get(response.jobId());

        while (retryCount < maxRetries) {
            try {
                // 메시지 재처리 로직: DB 업데이트 및 상태 전송
                documentRepository.updateStats(response.docId(), response.state());
                if (emitter != null) {
                    sendUpdate(emitter, response);
                }
                return; // 성공 시 재시도 종료
            } catch (Exception retryException) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    log.error("최대 재시도 횟수 초과: 문서 ID {}, 상태 {}, 오류 {}",
                            response.docId(), response.state(), retryException.getMessage());
                    if (emitter != null) {
                        emitter.completeWithError(retryException);
                        emitters.remove(response.jobId());
                    }
                }
            }
        }
    }

}
