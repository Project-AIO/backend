package com.idt.aio.service;

import com.idt.aio.entity.Document;
import com.idt.aio.entity.DocumentPart;
import com.idt.aio.repository.DocumentPartRepository;
import com.idt.aio.request.RuleData;
import com.idt.aio.response.DocumentPartResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentPartService {
    private final DocumentPartRepository documentPartRepository;

    @Transactional(readOnly = true)
    public List<DocumentPartResponse> getDocumentPartsByDocumentId(@NotNull final Integer documentId) {
        final List<DocumentPart> documentParts = documentPartRepository.findByDocument_DocId(documentId);
        return DocumentPartResponse.from(documentParts);
    }

    @Transactional
    public void saveDocumentPart(final Document document, @NotNull final List<RuleData> contents) {
        final List<DocumentPart> parts = contents.stream()
                .map(content -> DocumentPart.builder()
                        .document(document)
                        .title(content.title())
                        .startPage(content.startPage())
                        .endPage(content.endPage())
                        .build()).toList();

        documentPartRepository.saveAll(parts);
    }
}
