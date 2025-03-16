package com.idt.aio.service;

import com.idt.aio.response.ImageResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoreServerService {
    private final RestTemplate restTemplate;

    @Value("${core.server.url}")
    private String coreServerUrl;

    @Transactional
    public ImageResponse execute(final MultipartFile file, final int startPage, final int endPage) {

        // 요청 본문 생성
        MultiValueMap<String, Object> body = createRequest(file, startPage, endPage);


        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // HttpEntity 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // POST 요청 보내고 응답 받기
        try{
            ResponseEntity<ImageResponse> response = restTemplate.postForEntity(
                    coreServerUrl,
                    requestEntity,
                    ImageResponse.class
            );
            return response.getBody();
        }catch (Exception e){
            log.error("Core 서버 호출 실패: {}", e.getMessage());
            throw new RuntimeException("코어 서버와 통신 중 오류가 발생했습니다.", e);
        }
    }

    private MultiValueMap<String, Object> createRequest(final MultipartFile file, final int startPage, final int endPage) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            // 파일을 ByteArrayResource로 감싸서 전송
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            map.add("file", byteArrayResource);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
        // 다른 파라미터 추가
        map.add("start_page", String.valueOf(startPage));
        map.add("end_page", String.valueOf(endPage));
        return map;
    }

}
