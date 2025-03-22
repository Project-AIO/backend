package com.idt.aio.dto;

import com.idt.aio.entity.Document;
import lombok.Builder;

@Builder
public record DocumentJob(
        Document document,
        String jobId) {

}
