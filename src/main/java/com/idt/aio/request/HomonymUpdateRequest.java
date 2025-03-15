package com.idt.aio.request;

import lombok.Builder;

@Builder
public record HomonymUpdateRequest(
        Integer homonymId,
        String source,
        String match
) {
}
