package com.idt.aio.request;

import com.idt.aio.entity.constant.Feature;
import lombok.Builder;

@Builder
public record LanguageModelRequest(
    Integer projectId,
    Integer langModelId,
    String name,
    String vendor,
    Feature feature,
    String apiKey
) {
}
