package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SynonymRequest(
        @NotNull
        @Schema(name = "project_id")
        Integer projectId,
        @NotNull
        @Size(min = 1, max = 25)
        @Schema(name = "source")
        String source,
        @NotNull
        @Size(min = 1, max = 25)
        @Schema(name = "match")
        String match
) {
}
