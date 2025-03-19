package com.idt.aio.request;

import lombok.Builder;

@Builder
public record RuleData(
        int startPage,
        int endPage,
        String title
) {
}
