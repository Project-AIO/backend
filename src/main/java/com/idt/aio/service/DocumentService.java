package com.idt.aio.service;

import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.FeedbackDto;
import com.idt.aio.entity.Document;
import com.idt.aio.repository.DocumentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    public List<DocumentDto> fetchDocumentByFolderId(final Integer folderId) {
        final List<Document> documents = documentRepository.findByProjectFolder_ProjectFolderId(
                folderId);

        return DocumentDto.from(documents);
    }
}
