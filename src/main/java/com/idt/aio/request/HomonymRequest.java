package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record HomonymRequest(
        @NotNull
        @Schema(name = "project_id")
        Integer projectId,
        @NotNull
        @Schema(name = "source")
        @Size(min = 1, max = 25)
        String source,
        @Size(min = 1, max = 25)
        @NotNull
        @Schema(name = "match")
        String match
) {
}
