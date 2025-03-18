package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ConversationRequest(
        @NotNull
        Integer projectId,
        @Size(min = 1, max = 50)
        @NotNull
        String title
) {
}
