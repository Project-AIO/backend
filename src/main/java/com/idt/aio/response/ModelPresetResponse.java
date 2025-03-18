package com.idt.aio.response;

import com.idt.aio.entity.ModelPreset;
import lombok.Builder;

import java.util.List;

@Builder
public record ModelPresetResponse (
        Integer modelPresetId,
        Integer langModelId,
        Float temperature,
        Float topP,
        Integer topK
){
    public static List<ModelPresetResponse> from(final List<ModelPreset> entities){
        return entities.stream()
                .map(e-> ModelPresetResponse.builder()
                        .modelPresetId(e.getModelPresetId())
                        .langModelId(e.getLanguageModel().getLangModelId())
                        .temperature(e.getTemperature())
                        .topP(e.getTopP())
                        .topK(e.getTopK())
                        .build())
                .toList();
    }
    public static ModelPresetResponse from(final ModelPreset entity){
        return ModelPresetResponse.builder()
                .modelPresetId(entity.getModelPresetId())
                .langModelId(entity.getLanguageModel().getLangModelId())
                .temperature(entity.getTemperature())
                .topP(entity.getTopP())
                .topK(entity.getTopK())
                .build();
    }
}
