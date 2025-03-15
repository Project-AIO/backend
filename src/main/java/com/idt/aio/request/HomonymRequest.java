package com.idt.aio.request;

import lombok.Builder;

@Builder
public record HomonymRequest(
        Integer projectId,
        Integer homonymId,
        String source,
        String match
) {
}
