package com.idt.aio.controller;

import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.DocumentPathDto;
import com.idt.aio.dto.FileDto;
import com.idt.aio.entity.Document;
import com.idt.aio.request.DocumentUploadRequest;
import com.idt.aio.response.ImagePageResponse;
import com.idt.aio.service.*;
import com.idt.aio.validator.FileValidator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class  DocumentController {
    private final DocumentService documentService;
    private final DocumentPartService documentPartService;
    private final DocumentImageService documentImageService;
    private final FileValidator validator;

    @Operation(summary = "프로젝트 폴더 ID로 문서 가져오는 API", description = """
               프로젝트 ID로 피드백 가져오기
            """)
    @GetMapping("/documents")
    public List<DocumentDto> getDocumentsByFolderId(@RequestParam("project_folder_id") final Integer folderId) {
        return documentService.fetchDocumentByFolderId(folderId);
    }


    @Operation(summary = "문서 ID로 문서 삭제 API", description = """
               삭제하려는 문서와 연관된 테이블의 데이터들과 문서 파일도 삭제됨
            """)
    @DeleteMapping("/documents/{doc_id}")
    public ResponseEntity<?> deleteDocument(@PathVariable("doc_id") final Integer docId) {
        documentService.deleteDocumentById(docId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "'파일 불러오기' 버튼 클릭 시 프로젝트폴더 ID로 PDF 파일과 파라미터를 받아서 이미지 반환 API", description = """
               프로젝트폴더 ID로 PDF 파일과 파라미터를 받아서 이미지 반환 - [주의] swagger문서의 response 중 imageFile의 string은 이미지 파일 resource 타입임
            """)
    @PostMapping("/document/extract")
    public ImagePageResponse getDocumentImagePages(@RequestParam("file") final MultipartFile file,
                                                   @RequestParam("startPage") final Integer startPage,
                                                   @RequestParam("endPage") final Integer endPage) {
        //검증 현재는 PDF만 가능
        validator.validateFileSize(file);

        final FileDto fileDto = FileDto.from(file, startPage, endPage);

        return documentService.fetchImages(fileDto);
    }

    /**
     * 현재 tb_doc_part와 tb_doc_image 테이블에 넣는 부분은 빠져 있음
     */
    @Operation(summary = "'파일 추가 진행하기' 버튼 클릭 시 PDF 파일과 파라미터를 받아서 이미지를 CoreServer 에 전송하는 API", description = """
               '파일 추가 진행하기' 버튼 클릭 시 PDF 파일과 파라미터를 받아서 이미지를 CoreServer 에 전송 후 (tb_doc_part, tb_doc_image에 저장할 지 미정)
            """)
    @PostMapping("/document/upload")
    public ResponseEntity<?> uploadDocument(@ModelAttribute @Valid final DocumentUploadRequest request) {
        //검증 현재는 PDF만 가능
        validator.validateFileSize(request.file());
        Document document = documentService.processTransfer(request);
        documentImageService.saveDocumentImage(document, request.removePages());
       //  documentPartService.saveDocumentPart(document, request.contentStartPage(), request.contentEndPage());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Core Server에서 특정 문서를 다 학습하고 상태값 바꿀 때 쓰는 API", description = """
               Core Server에서 특정 문서를 다 학습하고 상태값 바꾸기
            """)
    @PostMapping("/document/status")
    public ResponseEntity<?> updateDocumentStatus(@RequestParam("docId") final Integer docId,
                                                  @RequestParam("status") final String status) {
        documentService.updateStatus(docId,status);
        return ResponseEntity.ok().build();
    }



}
