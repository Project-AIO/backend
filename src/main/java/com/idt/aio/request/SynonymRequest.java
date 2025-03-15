package com.idt.aio.request;

import lombok.Builder;

@Builder
public record SynonymRequest(
        Integer projectId,
        Integer synonymId,
        String source,
        String match
) {
}
