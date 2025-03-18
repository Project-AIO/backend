package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ConversationRequest(
        @NotNull
        Integer projectId,
        @NotNull
        String title
) {
}
