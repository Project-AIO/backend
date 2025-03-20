package com.idt.aio.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SimilarityDocRequest(
        @NotNull
        @Schema(name = "doc_id")
        Integer docId,
        @NotNull
        @Schema(name = "answer_id")
        Integer answerId,
        @NotNull
        @Schema(name = "page")
        int page,
        @Schema(name = "score")
        Float score
) {
}
