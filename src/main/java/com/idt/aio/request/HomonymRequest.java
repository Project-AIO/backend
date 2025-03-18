package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record HomonymRequest(
        @NotNull
        Integer projectId,
        @NotNull
        Integer homonymId,
        String source,
        String match
) {
}
