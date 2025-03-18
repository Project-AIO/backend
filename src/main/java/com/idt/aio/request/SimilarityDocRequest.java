package com.idt.aio.request;

import jakarta.validation.constraints.NotNull;

public record SimilarityDocRequest(
        @NotNull
        Integer docId,
        @NotNull
        Integer answerId,
        int page,
        Float score
) {
}
