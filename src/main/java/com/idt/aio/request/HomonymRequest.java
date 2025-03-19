package com.idt.aio.request;

import lombok.Builder;

@Builder
public record HomonymRequest(
        Integer projectId,
        String source,
        String match
) {
}
