package com.idt.aio.response;

import com.idt.aio.entity.ModelPreset;
import java.util.List;
import lombok.Builder;

@Builder
public record ModelPresetResponse(
        Integer modelPresetId,
        Integer langModelId,
        Float temperature,
        Float topP,
        Integer topK
) {
    public static List<ModelPresetResponse> from(final List<ModelPreset> entities) {
        return entities.stream()
                .map(ModelPresetResponse::from)
                .toList();
    }

    public static ModelPresetResponse from(final ModelPreset entity) {
        return ModelPresetResponse.builder()
                .modelPresetId(entity.getModelPresetId())
                .langModelId(entity.getLanguageModel().getLangModelId())
                .temperature(entity.getTemperature())
                .topP(entity.getTopP())
                .topK(entity.getTopK())
                .build();
    }
}
