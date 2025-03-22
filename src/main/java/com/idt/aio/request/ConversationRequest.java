package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ConversationRequest(
        @NotNull
        @Schema(name = "project_id")
        Integer projectId,
        @Size(min = 1, max = 50)
        @NotNull
        @Schema(name = "title")
        String title
) {
}
