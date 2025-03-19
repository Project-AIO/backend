package com.idt.aio.response;

import com.idt.aio.entity.SimilarityDoc;
import java.util.List;
import lombok.Builder;

@Builder
public record SimilarityDocResponse(
        Integer similarityDocId,
        Integer answerId,
        Integer docId,
        Integer page,
        Float score
) {
    public static SimilarityDocResponse from(final SimilarityDoc similarityDoc) {
        return SimilarityDocResponse.builder()
                .similarityDocId(similarityDoc.getSimilarityDocId())
                .answerId(similarityDoc.getAnswer().getAnswerId())
                .docId(similarityDoc.getDocument().getDocId())
                .page(similarityDoc.getPage())
                .score(similarityDoc.getScore())
                .build();
    }

    public static List<SimilarityDocResponse> from(final List<SimilarityDoc> similarityDocs) {
        return similarityDocs.stream()
                .map(SimilarityDocResponse::from)
                .toList();
    }
}
