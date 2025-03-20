package com.idt.aio.service;

import com.idt.aio.dto.*;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.ProjectFolder;
import com.idt.aio.entity.constant.Folder;
import com.idt.aio.entity.constant.State;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.extractor.AbstractFileExtractor;
import com.idt.aio.factory.FileExtractorFactory;
import com.idt.aio.repository.DocumentRepository;
import com.idt.aio.repository.ProjectFolderRepository;
import com.idt.aio.repository.ProjectRepository;
import com.idt.aio.request.DocumentUploadRequest;
import com.idt.aio.response.ContentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final CoreServerService coreServerService;
    private final FileService fileService;
    private final ConfigurationKnowledgeService configurationKnowledgeService;
    private final ProjectRepository projectRepository;
    private final ProjectFolderRepository projectFolderRepository;
    private final FileExtractorFactory fileExtractorFactory;

    @Transactional(readOnly = true)
    public List<DocumentDto> fetchDocumentByFolderId(final Integer folderId) {
        final List<Document> documents = documentRepository.findByProjectFolder_ProjectFolderId(
                folderId);

        return DocumentDto.from(documents);
    }

    @Transactional
    public DocumentJob processTransfer(final DocumentUploadRequest request) {
        final String extension = fileService.getFileExtension(request.file());
        final AbstractFileExtractor extractor = fileExtractorFactory.getExtractor(extension);
        final Document extracted = buildDocument(
                fileService.getFileSize(request.file()),
                extractor.getTotalPages(request.file()),
                request.projectId(),
                request.projectFolderId(),
                request.fileName());

        //엔티티 db 저장 및 폴더 생성
        final DocumentPathDto documentPathDto = saveDocumentAndGetFolderPath(extracted, request.projectId());


        //파일 저장
        fileService.saveResourceToFolder(request.file(), documentPathDto.getPath(), request.fileName(), extension);

        final String savedFilePath = documentPathDto.getPath() + File.separator + request.fileName() + extension;
        final ConfigurationKnowledgeDto configurationKnowledgeDto = configurationKnowledgeService.fetchConfigKnowledgeByProjectId(
                request.projectId());

        //core server로 전송
        final String jobId = coreServerService.executeTransfer(
                savedFilePath,
                request.contents(),
                documentPathDto.getDocId(),
                configurationKnowledgeDto);

        extracted.updateState(State.STAND_BY);

        return DocumentJob.builder()
                .document(extracted)
                .jobId(jobId)
                .build();
    }

    @Transactional
    public List<ContentResponse> fetchImages(final FileDto fileDto) {
        final String extension = fileService.getFileExtension(fileDto.getFile());
        final AbstractFileExtractor extractor = fileExtractorFactory.getExtractor(extension);

        final Resource file = extractor.extractFilePagesAsResource(
                fileDto.getFile(),
                fileDto.getStartPage(),
                fileDto.getEndPage());

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

    @Transactional
    public void deleteDocumentById(final Integer docId) {
        documentRepository.deleteById(docId);
        final String path = fileService.findPathWithoutRootByFolderName(Path.of(FileService.ROOT_PATH),
                Folder.DOCUMENT.getFolderName(docId));
        fileService.deleteFolder(path);
    }

    public Document buildDocument(final long fileSize, int pageCount, final Integer projectId,
                                  final Integer projectFolderId, final String fileName) {

        String path = Folder.PROJECT_FOLDER.getProjectFolderName(projectId, projectFolderId);

        final boolean projectExists = projectRepository.existsById(projectId);

        if (!projectExists) {
            throw DomainExceptionCode.PROJECT_NOT_FOUND.newInstance();
        }

        final boolean projectFolderExists = projectFolderRepository.existsById(projectFolderId);
        if (!projectFolderExists) {
            throw new RuntimeException("프로젝트 폴더가 존재하지 않습니다.");
        }

        final ProjectFolder referenceById = projectFolderRepository.getReferenceById(projectFolderId);

        return Document.builder()
                .name(fileName)
                .fileSize(fileSize)
                .pageCount(pageCount)
                .url(path)
                .fileSize(fileSize)
                .projectFolder(referenceById)
                .state(State.PENDING)
                .build();
    }

}
