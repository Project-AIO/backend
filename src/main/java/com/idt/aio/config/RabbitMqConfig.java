package com.idt.aio.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfig {
    // Spring -> Core
    //Spring -> Core 방향 통신에 쓰일 큐/교환기/라우팅 키
    public static final String FILE_CONTENT_QUEUE = "pdf-chapter-queue";
    public static final String FILE_CONTENT_EXCHANGE = "pdf-chapter-exchange";
    public static final String FILE_CONTENT_ROUTING_KEY = "pdf.chapter.key";

    // Core -> Spring (결과)
    public static final String FILE_CONTENT_RESULT_QUEUE = "pdf-chapter-result-queue";
    public static final String FILE_CONTENT_RESULT_EXCHANGE = "pdf-chapter-result-exchange";
    public static final String FILE_CONTENT_RESULT_ROUTING_KEY = "pdf.chapter.result.key";

    // (1) Spring -> Core 큐 생성
    //durable = false이므로 운영환경에선 필요 시 true로 변경 필요 false는 메모리에 저장되기 때문에 서버 재시작 시 데이터 손실 가능
    @Bean
    public Queue fileContentQueue() {
        return QueueBuilder.durable(FILE_CONTENT_QUEUE)
                .withArgument("x-message-ttl", 86400000) // 메시지 TTL 설정 (예: 1일 = 86400000밀리초)
                .build();
    }


    //Direct Exchange(직접 교환기) 생성
    @Bean
    public DirectExchange fileContentExchange() {
        return new DirectExchange(FILE_CONTENT_EXCHANGE);
    }

    //Queue와 Exchange 사이를 이어주는 Binding을 생성
    @Bean
    public Binding fileContentBinding(Queue fileContentQueue, DirectExchange fileContentExchange) {
        return BindingBuilder.bind(fileContentQueue)
                .to(fileContentExchange)
                .with(FILE_CONTENT_ROUTING_KEY);
    }

    // (2) Core -> Spring
    @Bean
    public Queue fileContentResultQueue() {
        return QueueBuilder.durable(FILE_CONTENT_RESULT_QUEUE)
                .withArgument("x-message-ttl", 86400000) // 메시지 TTL 설정 (예: 1일 = 86400000밀리초)
                .build();
    }

    @Bean
    public DirectExchange fileContentResultExchange() {
        return new DirectExchange(FILE_CONTENT_RESULT_EXCHANGE);
    }

    @Bean
    public Binding fileContentResultBinding(
            Queue fileContentResultQueue, DirectExchange fileContentResultExchange) {
        return BindingBuilder.bind(fileContentResultQueue)
                .to(fileContentResultExchange)
                .with(FILE_CONTENT_RESULT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        // Jackson2JsonMessageConverter: Java 객체 <-> JSON 자동 변환
        return new Jackson2JsonMessageConverter();
    }
}
