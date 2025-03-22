package com.idt.aio.request;

import com.idt.aio.entity.constant.Feature;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LanguageModelRequest(
        @Schema(name = "project_id")
        Integer projectId,
        @Schema(name = "name")
        String name,
        @Schema(name = "vendor")
        String vendor,
        @Schema(name = "feature")
        Feature feature,
        @Schema(name = "api_key")
        String apiKey
) {
}
