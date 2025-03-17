package com.idt.aio.service;

import com.idt.aio.entity.DocumentImage;
import com.idt.aio.repository.DocumentImageRepository;
import com.idt.aio.repository.DocumentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentImageService {
    private final DocumentImageRepository documentImageRepository;
    private final DocumentRepository documentRepository;
    private final FileService fileService;


    @Builder
    public record ImageDto(
            Integer docImageId,
            Integer page
    ) {
        public static List<ImageDto> from(final List<DocumentImage> documentImages) {
            return documentImages.stream()
                    .map(documentImage -> ImageDto.builder()
                            .docImageId(documentImage.getDocImageId())
                            .page(documentImage.getPage())
                            .build())
                    .toList();
        }
    }
}
