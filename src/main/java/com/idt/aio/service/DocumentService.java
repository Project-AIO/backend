package com.idt.aio.service;

import com.idt.aio.dto.DocumentDto;
import com.idt.aio.entity.Document;
import com.idt.aio.repository.DocumentRepository;
import com.idt.aio.response.ImageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final CoreServerService coreServerService;

    public List<DocumentDto> fetchDocumentByFolderId(final Integer folderId) {
        final List<Document> documents = documentRepository.findByProjectFolder_ProjectFolderId(
                folderId);

        return DocumentDto.from(documents);
    }

    public ImageResponse fetchImages(final MultipartFile file, final int startPage, final int endPage) {

        //코어 서버로 전송 후 이미지 파일 반환
        return coreServerService.execute(file, startPage, endPage);
    }
}
