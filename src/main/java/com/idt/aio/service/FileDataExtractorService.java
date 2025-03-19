package com.idt.aio.service;

import com.idt.aio.entity.Document;
import com.idt.aio.entity.ProjectFolder;
import com.idt.aio.entity.constant.Folder;
import com.idt.aio.entity.constant.State;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.ProjectFolderRepository;
import java.io.IOException;

import com.idt.aio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileDataExtractorService {
    private final ProjectRepository projectRepository;
    private final ProjectFolderRepository projectFolderRepository;

    public int getPageCount(final MultipartFile file) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            return document.getNumberOfPages();
        } catch (IOException e) {
            throw new RuntimeException("PDF 파일 처리 중 오류가 발생했습니다.", e);
        }
    }

    public Document extractDocumentFromFile(final MultipartFile file, final Integer projectId,
                                            final Integer projectFolderId, final String fileName) {


        // 2. 파일 사이즈 추출 (바이트 단위)
        long fileSize = file.getSize();

        // 3. PDF 페이지 수 추출 (PDFBox 사용)
        int pageCount = getPageCount(file);

        String path = Folder.PROJECT_FOLDER.getProjectFolderName(projectId, projectFolderId);

        final boolean projectExists = projectRepository.existsById(projectId);

        if(!projectExists){
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
