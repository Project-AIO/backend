package com.idt.aio.request;

import lombok.Builder;

@Builder
public record SynonymUpdateRequest(
        String source,
        String match
) {
}
