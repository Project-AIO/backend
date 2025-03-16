package com.idt.aio.service;

import com.idt.aio.dto.DocumentImageDto;
import com.idt.aio.dto.ImageFileDto;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.DocumentImage;
import com.idt.aio.entity.constant.Folder;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.DocumentImageRepository;
import com.idt.aio.repository.DocumentRepository;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentImageService {
    private final DocumentImageRepository documentImageRepository;
    private final DocumentRepository documentRepository;
    private final FileService fileService;


    @Transactional
    public List<ImageDto> saveDocumentImage(final List<DocumentImageDto> documentImageDtos) {

        Integer docId = documentImageDtos.get(0).getDocId();

        if(documentRepository.existsById(docId)){
            throw DomainExceptionCode.DOCUMENT_NOT_FOUND.newInstance();
        }

        final Document referenceById = documentRepository.getReferenceById(docId);
        final List<DocumentImage> documentImages = DocumentImage.from(documentImageDtos, referenceById);
        final List<DocumentImage> save = documentImageRepository.saveAll(documentImages);


        return ImageDto.from(save);
    }



    @Builder
    public record ImageDto(
        Integer docImageId,
        Integer page
    ){
        public static List<ImageDto> from(final List<DocumentImage> documentImages){
            return documentImages.stream()
                    .map(documentImage -> ImageDto.builder()
                            .docImageId(documentImage.getDocImageId())
                            .page(documentImage.getPage())
                            .build())
                    .toList();
        }
    }
}
