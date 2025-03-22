package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ConfigKnowledgeRequest(
        @NotNull
        @Schema(name = "project_id")
        Integer projectId,
        @NotNull
        @Schema(name = "chunk_token_size")
        String chunkTokenSize,
        @NotNull
        @Schema(name = "overlap_token_rate")
        Float overlapTokenRate,
        @NotNull
        @Schema(name = "emb_model_name")
        @Size(max = 255)
        String embModelName,
        @Size(max = 255)
        @NotNull
        @Schema(name = "rerk_model_name")
        String rerkModelName,
        @NotNull
        @Schema(name = "rerk_top_n")
        Integer rerkTopN,
        @NotNull
        @Schema(name = "retv_threshold_score")
        Float retvThresholdScore,
        @NotNull
        @Schema(name = "retv_top_k")
        Integer retvTopK,
        @NotNull
        @Schema(name = "keyword_weight")
        Float keywordWeight

) {

}
