package com.idt.aio.service;

import com.idt.aio.config.RabbitMqConfig;
import com.idt.aio.request.ContentSenderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentSenderService {
    private final RabbitTemplate rabbitTemplate;

    public void sendContents(ContentSenderRequest request) {
        // convertAndSend(exchange, routingKey, messageObject)
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.FILE_CONTENT_EXCHANGE,
                RabbitMqConfig.FILE_CONTENT_ROUTING_KEY,
                request
        );
    }
}
