package com.idt.aio.service;

import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.DocumentPathDto;
import com.idt.aio.dto.FileDto;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.constant.Folder;
import com.idt.aio.entity.constant.State;
import com.idt.aio.repository.DocumentRepository;
import com.idt.aio.request.DocumentUploadRequest;
import com.idt.aio.response.ImagePageResponse;

import java.nio.file.Path;
import java.util.List;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    private final CoreServerService coreServerService;
    private final FileService fileService;
    private final FileDataExtractorService fileDataExtractorService;

    @Transactional(readOnly = true)
    public List<DocumentDto> fetchDocumentByFolderId(final Integer folderId) {
        final List<Document> documents = documentRepository.findByProjectFolder_ProjectFolderId(
                folderId);

        return DocumentDto.from(documents);
    }

    @Transactional
    public Document processTransfer(final DocumentUploadRequest request){
        final Document extracted = fileDataExtractorService.extractDocumentFromFile(
                request.file(),
                request.projectId(),
                request.projectFolderId(),
                request.fileName());

        //엔티티 db 저장 및 폴더 생성
        final DocumentPathDto documentPathDto = saveDocumentAndGetFolderPath(extracted, request.projectId());
        fileService.saveResourceToFolder(request.file(),documentPathDto.getPath() , request.fileName());

        //core server로 전송
        coreServerService.executeTransfer(
                documentPathDto.getPath(),
                request.fileName(),
                request.file(),
                1,
                fileDataExtractorService.getPageCount(request.file()),
                documentPathDto.getDocId());

        /**
         * 아직 세부 요구사항이 안 나와서 pending
         */
        return null;
    }

    @Transactional
    public ImagePageResponse fetchImages(final FileDto fileDto) {
        final MultipartFile file = fileDto.getFile();
        final int startPage = fileDto.getStartPage();
        final int endPage = fileDto.getEndPage();

        //코어 서버로 전송 후 이미지 파일 반환
        return coreServerService.executeExtraction(file, startPage, endPage);
    }


    @Transactional
    public DocumentPathDto saveDocumentAndGetFolderPath(final Document document, final Integer projectId) {

        //db저장
        final Document save = documentRepository.save(document);

        //폴더 생성
        final String path = Folder.DOCUMENT.getDocumentName(projectId, save.getProjectFolder().getProjectFolderId(),
                save.getDocId());
        fileService.createFolder(path);
        return DocumentPathDto.builder()
                .docId(save.getDocId())
                .path(path)
                .build();
    }

    @Transactional
    public void updateStatus(final Integer docId, final String status) {
        State state = State.valueOf(status);
        documentRepository.updateStats(docId, state);
    }

    public void deleteDocumentById(final Integer docId) {
        documentRepository.deleteById(docId);
        final String path = fileService.findPathWithoutRootByFolderName(Path.of(FileService.ROOT_PATH), Folder.DOCUMENT.getFolderName(docId));
        fileService.deleteFolder(path);
    }

    @Builder
    public record ImageData(
            Integer docImageId,
            Resource imageFile,
            int page) {

    }

}
