package com.idt.aio.request;

import lombok.Builder;

@Builder
public record SynonymRequest(
        Integer projectId,
        int page,
        int size
) {
}
