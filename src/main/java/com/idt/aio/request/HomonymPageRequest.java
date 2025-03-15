package com.idt.aio.request;

import lombok.Builder;

@Builder
public record HomonymPageRequest(
        Integer projectId,
        int page,
        int size
) {
}
