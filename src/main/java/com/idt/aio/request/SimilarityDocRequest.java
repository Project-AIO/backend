package com.idt.aio.request;

public record SimilarityDocRequest(
        Integer docId,
        Integer answerId,
        int page,
        Float score
) {
}
