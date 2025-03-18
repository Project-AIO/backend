package com.idt.aio.response;

import com.idt.aio.entity.LanguageModel;
import com.idt.aio.entity.constant.Feature;
import java.util.stream.Collectors;
import lombok.Builder;
import java.util.List;

@Builder
public record LanguageModelResponse(
        Integer langModelId,
        Integer projectId,
        String name,
        String vendor,
        Feature feature,
        String apiKey
) {
    public static LanguageModelResponse from (final LanguageModel entity){
        return LanguageModelResponse.builder()
                .langModelId(entity.getLangModelId())
                .projectId(entity.getProject().getProjectId())
                .name(entity.getName())
                .vendor(entity.getVendor())
                .feature(entity.getFeature())
                .apiKey(entity.getApiKey())
                .build();
    }
    public static List<LanguageModelResponse> from (final List<LanguageModel> entities){
        return entities.stream()
                .map(LanguageModelResponse::from)
                .toList();
    }
}
