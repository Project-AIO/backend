package com.idt.aio.request;

import lombok.Builder;

@Builder
public record ModelPresetRequest(
        Integer langModelId,
        Float temperature,
        Float topP,
        Integer topK
) {
}
