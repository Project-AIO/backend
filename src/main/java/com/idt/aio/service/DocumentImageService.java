package com.idt.aio.service;

import com.idt.aio.entity.Document;
import com.idt.aio.entity.DocumentImage;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.DocumentImageRepository;
import com.idt.aio.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentImageService {
    private final DocumentImageRepository documentImageRepository;
    private final DocumentRepository documentRepository;
    @Transactional

    public void saveDocumentImage(final Document document, final List<Integer> pages){
        if(!documentRepository.existsById(document.getDocId())){
            throw DomainExceptionCode.DOCUMENT_NOT_FOUND.newInstance(document.getDocId());
        }

        final List<DocumentImage> docImages = pages.stream().map(p -> DocumentImage.builder()
                        .document(document)
                        .docImageId(p)
                        .build())
                .toList();
        documentImageRepository.saveAll(docImages);

    }

}
