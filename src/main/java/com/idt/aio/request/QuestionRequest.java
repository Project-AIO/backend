package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record QuestionRequest(
        @NotNull
        @Schema(name = "conversation_id")
        Integer conversationId,
        @NotNull
        @Schema(name = "message")
        String message
) {
}
