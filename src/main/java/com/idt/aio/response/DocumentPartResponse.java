package com.idt.aio.response;

import com.idt.aio.entity.DocumentPart;
import lombok.Builder;

import java.util.List;

@Builder
public record DocumentPartResponse(
        Integer docId,
        Integer docPartId,
        String title,
        Integer startPage,
        Integer endPage,
        String label
) {
    public static List<DocumentPartResponse> from(final List<DocumentPart> documentParts) {
        return documentParts.stream()
                .map(documentPart -> DocumentPartResponse.builder()
                        .docId(documentPart.getDocPartId())
                        .docPartId(documentPart.getDocPartId())
                        .title(documentPart.getTitle())
                        .startPage(documentPart.getStartPage())
                        .endPage(documentPart.getEndPage())
                        .label(documentPart.getLabel())
                        .build())
                .toList();
    }
}
