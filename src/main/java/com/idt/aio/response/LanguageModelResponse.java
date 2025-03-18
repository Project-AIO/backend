package com.idt.aio.response;

import com.idt.aio.entity.LanguageModel;
import com.idt.aio.entity.constant.Feature;
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
    public static List<LanguageModelResponse> from (final List<LanguageModel> entities){
        return entities.stream()
                .map(e-> LanguageModelResponse.builder()
                        .langModelId(e.getLangModelId())
                        .projectId(e.getProject().getProjectId())
                        .name(e.getName())
                        .vendor(e.getVendor())
                        .feature(e.getFeature())
                        .apiKey(e.getApiKey())
                        .build())
                .toList();
    }
}
