package com.idt.aio.request;

import lombok.Builder;

@Builder
public record SynonymPageRequest(
        Integer projectId,
        int page,
        int size
) {
}
