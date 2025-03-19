package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record HomonymRequest(
        @NotNull
        Integer projectId,
        @NotNull
        @Size(min = 1, max = 25)
        String source,
        @Size(min = 1, max = 25)
        @NotNull
        String match
) {
}
