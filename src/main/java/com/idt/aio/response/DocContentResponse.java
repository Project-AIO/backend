package com.idt.aio.response;

import lombok.Builder;

@Builder
public record DocContentResponse(
        String title,
        int startPage,
        int endPage
) {
}
