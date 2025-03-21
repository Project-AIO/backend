package com.idt.aio.service;

import com.idt.aio.dto.*;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.DocumentFile;
import com.idt.aio.entity.ProjectFolder;
import com.idt.aio.entity.constant.Folder;
import com.idt.aio.entity.constant.State;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.extractor.AbstractFileExtractor;
import com.idt.aio.factory.FileExtractorFactory;
import com.idt.aio.repository.DocumentFileRepository;
import com.idt.aio.repository.DocumentRepository;
import com.idt.aio.repository.ProjectFolderRepository;
import com.idt.aio.repository.ProjectRepository;
import com.idt.aio.request.RuleData;
import com.idt.aio.response.ContentResponse;
import com.idt.aio.response.DocumentData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final DocumentFileRepository documentFileRepository;
    private final FileExtractorFactory fileExtractorFactory;

    @Transactional(readOnly = true)
    public List<DocumentData> fetchDocumentByFolderId(final Integer folderId) {
        return documentRepository.findDocumentDataByProjectId(folderId);
    }
    @Transactional(readOnly = true)
    public DocumentData fetchDocumentFileDataById(final Integer docId){
        
        return documentRepository.findDocumentDataById(docId).orElseThrow(DomainExceptionCode.DOCUMENT_NOT_FOUND::newInstance);
    }

    @Transactional
    public DocumentJob executeSaveAndTransfer(final MultipartFile file,
                                              final Integer projectId,
                                              final Integer projectFolderId,
                                              final String fileName,
                                              final String revision,
                                              final List<RuleData> contents) {
        final String extension = fileService.getFileExtension(file);
        final AbstractFileExtractor extractor = fileExtractorFactory.getExtractor(extension);

        //Document 엔티티 생성
         final Document extracted = buildDocument(
                projectId,
                projectFolderId,
                fileName);

        //Document 엔티티 db 저장 및 폴더 생성
        final DocumentPathDto documentPathDto = saveDocumentAndGetFolderPath(extracted, projectId);

        //문서 파일이 저장된 절대 경로
        final String absoluteFilePath = documentPathDto.getPath();
        //DocumentFile 엔티티 생성
        final DocumentFile documentFile = buildDocumentFile(extractor, file, absoluteFilePath, extracted, revision);

        //파일 저장
        documentFileRepository.save(documentFile);

        //물리 파일 저장
        fileService.saveResourceToFolder(file, absoluteFilePath, fileName, extension);

        //경로+파일명
        final String savedFilePath = absoluteFilePath + File.separator + fileName + "." + extension;
        final ConfigurationKnowledgeDto configurationKnowledgeDto = configurationKnowledgeService.fetchConfigKnowledgeByProjectId(
                projectId);

        //core server로 전송
        final String jobId = coreServerService.executeTransfer(
                savedFilePath,
                contents,
                documentPathDto.getDocId(),
                configurationKnowledgeDto);

        //상태 변경
        extracted.updateState(State.READY);

        return DocumentJob.builder()
                .document(extracted)
                .jobId(jobId)
                .build();
    }

    private DocumentFile buildDocumentFile(final AbstractFileExtractor extractor,
                                           final MultipartFile file,
                                           final String savedFolderPath,
                                           final Document extractedDocument,
                                           final String revision) {
        final String fileExtension = fileService.getFileExtension(file);
        final int totalPages = extractor.getTotalPages(file);
        final long fileSize = fileService.getFileSize(file);
        final String baseFileName = fileService.getFileNameWithoutExtension(file);
        final String fileNameWithExtension = file.getOriginalFilename();

        return DocumentFile.builder()
                .document(extractedDocument)
                .extension(fileExtension)
                .path(savedFolderPath)
                .fileName(baseFileName)
                .totalPage(totalPages)
                .originalFileName(fileNameWithExtension)
                .fileSize(fileSize)
                .revision(revision)
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
    protected DocumentPathDto saveDocumentAndGetFolderPath(final Document document, final Integer projectId) {

        //db저장
        final Document save = documentRepository.save(document);

        //폴더 생성
        final String folder = Folder.DOCUMENT.getDocumentFolderPath(projectId, save.getProjectFolder().getProjectFolderId(),
                save.getDocId());
        final String absolutePath = fileService.createFolder(folder);

        return DocumentPathDto.builder()
                .docId(save.getDocId())
                .path(absolutePath)
                .build();
    }


    @Transactional
    public void deleteDocumentById(final Integer docId) {
        //폴더명 전까지의 절대경로
        final String absolutePath = documentFileRepository.findPathByDocument_DocId(docId);
        documentFileRepository.deleteByDocument_DocId(docId);
        documentRepository.deleteById(docId);

       fileService.deleteFolderByAbsolutePath(absolutePath);
    }

    private Document buildDocument(final Integer projectId, final Integer projectFolderId, final String fileName) {

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
                .projectFolder(referenceById)
                .state(State.PENDING)
                .build();
    }

}
