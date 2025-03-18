package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ConfigKnowledgeUpdateRequest(
        @NotNull
        Integer confKnowledgeId,
        @NotNull
        String chunkTokenSize,
        @NotNull
        Float overlapTokenRate,
        @NotNull
        @Size(max = 255)
        String embModelName,
        @Size(max = 255)
        @NotNull
        String rerkModelName,
        @NotNull
        Integer rerktopN,
        @NotNull
        Float retvThreshholdScore,
        @NotNull
        Integer retvTopK,
        @NotNull
        Float keywordWeight

) {

}
