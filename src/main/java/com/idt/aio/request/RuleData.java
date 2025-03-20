package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RuleData(
        @Schema(name = "start_page")
        @NotNull
        int startPage,
        @Schema(name = "end_page")
        @NotNull
        int endPage,
        @Schema(name = "title")
        @NotNull
        String title
) {
}
