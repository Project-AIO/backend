package com.idt.aio.request;

import lombok.Builder;

@Builder
public record HomonymUpdateRequest(
        String source,
        String match
) {
}
