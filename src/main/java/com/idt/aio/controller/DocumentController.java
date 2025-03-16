package com.idt.aio.controller;

import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.FileDto;
import com.idt.aio.entity.Document;
import com.idt.aio.request.DocumentUploadRequest;
import com.idt.aio.response.ImageFileResponse;
import com.idt.aio.service.DocumentService;
import com.idt.aio.service.FileDataExtractorService;
import com.idt.aio.validator.FileValidator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DocumentController {
    private final DocumentService documentService;
    private final FileValidator validator;
    private final FileDataExtractorService fileDataExtractorService;

    @Operation(summary = "프로젝트 폴더 ID로 문서 가져오는 API", description = """
               프로젝트 ID로 피드백 가져오기
            """)
    @GetMapping("folders/{project_folder_id}/documents")
    public List<DocumentDto> getDocumentsByFolderId(@PathVariable("project_folder_id") final Integer folderId) {
        return documentService.fetchDocumentByFolderId(folderId);
    }

    @Operation(summary = "'파일 불러오기' 버튼 클릭 시 프로젝트폴더 ID로 PDF 파일과 파라미터를 받아서 이미지 반환 API", description = """
               프로젝트폴더 ID로 PDF 파일과 파라미터를 받아서 이미지 반환 - [주의] swagger문서의 response 중 imageFile의 string은 이미지 파일 resource 타입임
            """)
    @PostMapping("/document/upload")
    public ImageFileResponse getDocumentImages(@ModelAttribute @Valid DocumentUploadRequest request) {
        //검증 현재는 PDF만 가능
        validator.validateFileSize(request.file());

        //MultipartFile로부터 DocumentData 추출, 현재는 PDF라고 가정했을 때
        final Document document = fileDataExtractorService.extractDocumentFromFile(request.file(), request.projectId(),
                request.projectFolderId());

        //db에 document 저장
        documentService.saveDocument(document, request.projectId());

        final FileDto fileDto = FileDto.from(request.file(), 1, document.getPageCount());

        return documentService.fetchImages(fileDto, request.projectId());
    }
}
