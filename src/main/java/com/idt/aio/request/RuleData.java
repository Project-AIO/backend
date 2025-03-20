package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RuleData(
        @NotNull
        int startPage,
        @NotNull
        int endPage,
        @NotNull
        String title
) {
}
