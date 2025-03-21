package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SynonymUpdateRequest(
        @NotNull
        @Size(min = 1, max = 25)
        String source,
        @NotNull
        @Size(min = 1, max = 25)
        String match
) {
}
