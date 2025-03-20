package com.idt.aio.service;

import com.idt.aio.dto.ConfigurationKnowledgeDto;
import com.idt.aio.request.ContentSenderRequest;
import com.idt.aio.request.RuleData;
import com.idt.aio.response.ContentResponse;
import com.idt.aio.response.DataResponse;
import com.idt.aio.util.JobIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoreServerService {
    private final RestTemplate restTemplate;
    private final ContentSenderService sender;
    private final JobIdUtil jobIdUtil;

    @Value("${core.server.url}")
    private String coreServerUrl;

    public String executeTransfer(
            final String savedFilePath,
            final List<RuleData> contents,
            final Integer docId,
            final ConfigurationKnowledgeDto config
    ) {
        final String jobId = jobIdUtil.generateJobId();

        final ContentSenderRequest request = ContentSenderRequest.builder()
                .jobId(jobId)
                .filePath(savedFilePath)
                .rules(contents)
                .docId(docId)
                .chunkSize(config.getChunkSize())
                .empModelName(config.getEmbModelName())
                .overlapTokenRate(config.getOverlapTokenRate())
                .build();

        final DataResponse<ContentSenderRequest> dataResponse = DataResponse.from(request);

        sender.sendContents(dataResponse);
        return jobId;
    }

    @Transactional
    public List<ContentResponse> executeExtraction(final Resource file, final int startPage, final int endPage) {
        // 요청 본문 생성
        final MultiValueMap<String, Object> body = createRequest(file, startPage, endPage);

        // 헤더 설정
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // HttpEntity 생성
        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // POST 요청 보내고 응답 받기
        try {
            ResponseEntity<List<ContentResponse>> response = restTemplate.exchange(
                    coreServerUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            return response.getBody();
        } catch (Exception e) {
            log.error("Core 서버 호출 실패: {}", e.getMessage());
            throw new RuntimeException("코어 서버와 통신 중 오류가 발생했습니다.", e);
        }
    }

    private MultiValueMap<String, Object> createRequest(final Resource file, final int startPage, final int endPage) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            // Resource의 InputStream에서 바이트 배열 추출
            byte[] bytes = FileCopyUtils.copyToByteArray(file.getInputStream());
            // ByteArrayResource를 사용하여 파일 데이터를 전송.
            // Resource의 getFilename() 메서드를 사용하여 파일 이름 반환
            ByteArrayResource byteArrayResource = new ByteArrayResource(bytes) {
                @Override
                public String getFilename() {
                    return file.getFilename();
                }
            };
            map.add("file", byteArrayResource);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
        // 추가 파라미터 설정
        map.add("start_page", String.valueOf(startPage));
        map.add("end_page", String.valueOf(endPage));
        return map;
    }

    private Resource multipartFileToResource(MultipartFile file) {
        try {
            return new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("MultipartFile을 Resource로 변환하는 중 오류 발생", e);
        }
    }

}
