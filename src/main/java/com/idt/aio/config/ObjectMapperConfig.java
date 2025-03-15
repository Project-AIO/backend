package com.idt.aio.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    /**
     * ObjectMapper objectMapper = new ObjectMapper();: ObjectMapper 객체를 생성합니다.
     * <p>
     * objectMapper.registerModule(new Jdk8Module());: Jdk8Module을 등록합니다. 이 모듈은 JDK 8 버전 이상에서 사용할 수 있는 클래스들을 JSON 파싱,
     * 직렬화, 역직렬화하기 위한 설정을 제공합니다.
     * <p>
     * objectMapper.registerModule(new JavaTimeModule());: JavaTimeModule을 등록합니다. 이 모듈은 Java 8에서 추가된 java.time 패키지에 있는
     * 날짜와 시간 관련 클래스들을 JSON으로 직렬화하고 역직렬화할 수 있도록 지원합니다.
     * <p>
     * objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);: 알 수 없는 JSON 필드에 대해 무시하도록
     * 설정합니다. 즉, JSON 데이터에 알 수 없는 필드가 있을 때 에러를 발생시키지 않고 무시합니다.
     * <p>
     * objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);: 빈 객체를 JSON으로 직렬화할 때, 빈 객체인 경우에도 에러를
     * 발생시키지 않고 무시합니다.
     * <p>
     * objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);: 날짜를 직렬화할 때 타임스탬프 대신 ISO-8601 형식으로 직렬화하도록
     * 설정합니다. WRITE_DATES_AS_TIMESTAMPS 기능을 비활성화하면, ObjectMapper는 날짜와 시간 객체를 ISO 8601 형식 (yyyy-MM-dd'T'HH:mm:ss.SSSZ)의 문자열로 출력함.
     * 이 형식은 국제 표준이며, 다양한 프로그래밍 언어와 시스템에서 널리 인식되고 지원됨.
     * 예를 들어, 2023-10-03T13:55:20.000+0000 같은 형태로 출력
     * <p>
     * objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());: JSON 필드의 네이밍 전략을 스네이크
     * 케이스로 설정합니다. 즉, 필드명을 CamelCase가 아닌 스네이크 케이스로 변경합니다.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(
                new Jdk8Module()); // jdk 8 버젼 이후 클래스를 parsing, deserialize, serialize하기 위한 것들이 들어가 있음

        objectMapper.registerModule(new JavaTimeModule()); //<< local date에 해당

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //모르는 json field에 대해서는 무시한다.

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 날짜 관련 직렬화
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //스네이크 케이스
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        return objectMapper;
    }
}
