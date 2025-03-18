package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record HomonymRequest(
        @NotNull
        Integer projectId,
        @NotNull
        Integer homonymId,
        @Size(min = 1, max = 25)
        @NotNull
        String source,
        @Size(min = 1, max = 25)
        @NotNull
        String match
) {
}
