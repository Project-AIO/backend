package com.idt.aio.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JobIdUtil {
    public String generateJobId() {
        return UUID.randomUUID().toString();
    }
}
