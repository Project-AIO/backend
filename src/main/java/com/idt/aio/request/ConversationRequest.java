package com.idt.aio.request;

import lombok.Builder;

@Builder
public record ConversationRequest(
        Integer projectId,
        String title
) {
}
