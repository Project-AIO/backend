package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SynonymRequest(
        @NotNull
        Integer projectId,
        @NotNull
        Integer synonymId,
        String source,
        String match
) {
}
