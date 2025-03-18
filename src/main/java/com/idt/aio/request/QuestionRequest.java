package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record QuestionRequest(
        @NotNull
        Integer conversationId,
        @NotNull
        String message
) {
}
