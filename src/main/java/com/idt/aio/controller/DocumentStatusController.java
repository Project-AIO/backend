package com.idt.aio.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DocumentStatusController {
    private final Map<String, SseEmitter> emitters;

    @Operation(summary = "문서 상태 조회를 위한 SSE 구독 API", description = """
              문서의 JobId로 상태를 조회하기 위한 SSE 연결을 생성
            """)
    @GetMapping("/sse/subscribe/{job_id}")
    public SseEmitter subscribe(@PathVariable("job_id") final String jobId) {
        // 하루
        SseEmitter emitter = new SseEmitter(3600000L);

        // 연결 완료 혹은 타임아웃 시 해당 emitter를 제거
        emitter.onCompletion(() -> emitters.remove(jobId));
        emitter.onTimeout(() -> emitters.remove(jobId));
        emitter.onError((e) -> emitters.remove(jobId));

        // documentId를 key로 저장 (여러 문서의 연결을 동시에 관리할 수 있음)
        emitters.put(jobId, emitter);

        return emitter;
    }
}
