package com.idt.aio.util;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class JobIdUtil {
    public String generateJobId() {
        return UUID.randomUUID().toString();
    }
}
