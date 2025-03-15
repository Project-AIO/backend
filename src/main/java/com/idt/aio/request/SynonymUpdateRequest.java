package com.idt.aio.request;

import lombok.Builder;

@Builder
public record SynonymUpdateRequest(
        Integer synonymId,
        String source,
        String match
) {
}
