package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ModelPresetRequest(
        @NotNull
        @Schema(name = "lang_model_id")
        Integer langModelId,
        @NotNull
        @Schema(name = "temperature")
        Float temperature,
        @NotNull
        @Schema(name = "top_p")
        Float topP,
        @NotNull
        @Schema(name = "top_k")
        Integer topK
) {
}
