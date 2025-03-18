package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ModelPresetRequest(
        @NotNull
        Integer langModelId,
        @NotNull
        Float temperature,
        @NotNull
        Float topP,
        @NotNull
        Integer topK
) {
}
