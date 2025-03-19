package com.idt.aio.request;

import lombok.Builder;

@Builder
public record ContentData(
        int startPage,
        int endPage,
        String title
) {
}
