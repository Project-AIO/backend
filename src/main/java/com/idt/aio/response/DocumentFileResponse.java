package com.idt.aio.response;


import lombok.Builder;
import org.springframework.core.io.Resource;

import java.util.List;

@Builder
public record DocumentFileResponse(
        DocumentData documentData,
        List<DocumentPartResponse> partsDto,
        Resource file
) {
    public static DocumentFileResponse of(DocumentData documentData, List<DocumentPartResponse> partsDto, Resource file) {
        return DocumentFileResponse.builder()
                .documentData(documentData)
                .partsDto(partsDto)
                .file(file)
                .build();
    }
}
