package com.idt.aio.service;

import com.idt.aio.config.RabbitMqConfig;
import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.DocumentPathDto;
import com.idt.aio.dto.FileDto;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.constant.Folder;
import com.idt.aio.entity.constant.State;
import com.idt.aio.repository.DocumentRepository;
import com.idt.aio.request.DocumentUploadRequest;
import com.idt.aio.response.ContentResponse;

import java.nio.file.Path;
import java.util.List;

import com.idt.aio.response.CoreServerResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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
        final String extension = fileService.getFileExtension(request.file());
        fileService.saveResourceToFolder(request.file(),documentPathDto.getPath() , request.fileName(), extension);

        //core server로 전송
        coreServerService.executeTransfer(
                documentPathDto.getPath(),
                request.fileName(),
                request.contents(),
                documentPathDto.getDocId());

        extracted.updateState(State.STAND_BY);

        return extracted;
    }

    @Transactional
    @RabbitListener(queues = RabbitMqConfig.FILE_CONTENT_RESULT_QUEUE)
    public void handleChapterResult(CoreServerResponse response) {
        log.info("코어 서버에서 처리 결과를 수신했습니다: {}", response);

        // 여기서 할 수 있는 작업 예시:
        // 1. DB에 해당 챕터의 처리 상태 업데이트
        // 2. WebSocket이나 SSE 등을 통해 프론트(Vue)에 "처리 완료" 알림 보내기
        // 3. 에러 시 재시도 로직 등

        if (response.state().equals(State.SERVING)) {
            log.info("문서 ID : {} 처리 성공, 상태 : {}", response.docId(), response.state());
            documentRepository.updateStats(response.docId(), State.SERVING);
        } else {
            log.warn("문서 ID {} 처리 실패, 상태 : {}",
                    response.docId(), response.state());
        }
    }

    @Transactional
    public List<ContentResponse> fetchImages(final FileDto fileDto) {
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
        final String folder = Folder.DOCUMENT.getDocumentName(projectId, save.getProjectFolder().getProjectFolderId(),
                save.getDocId());
        final String absolutePath = fileService.createFolder(folder);
        return DocumentPathDto.builder()
                .docId(save.getDocId())
                .path(absolutePath)
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
