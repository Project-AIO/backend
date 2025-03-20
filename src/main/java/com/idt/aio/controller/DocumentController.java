package com.idt.aio.controller;

import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.DocumentJob;
import com.idt.aio.dto.FileDto;
import com.idt.aio.request.RuleData;
import com.idt.aio.response.ContentResponse;
import com.idt.aio.response.DataResponse;
import com.idt.aio.service.DocumentPartService;
import com.idt.aio.service.DocumentService;
import com.idt.aio.service.FileService;
import com.idt.aio.validator.FileValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentPartService documentPartService;
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


    @Operation(summary = "'목차 만들기' 버튼 클릭 시 프로젝트폴더 ID로 PDF 파일과 파라미터를 받아서 이미지 반환 API", description = """
               프로젝트폴더 ID로 PDF 파일과 파라미터를 받아서 이미지 반환 
            """)
    @PostMapping(path = "/document/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DataResponse<List<ContentResponse>>> getDocumentImagePages(@RequestParam("file")  @Schema(name = "file")final MultipartFile file,
                                                              @RequestParam("startPage")  @Schema(name = "start_page") final Integer startPage,
                                                              @RequestParam("endPage")  @Schema(name = "end_page")final Integer endPage) {
        //검증 현재는 PDF만 가능
        validator.validateFileSize(file);

        final FileDto fileDto = FileDto.from(file, startPage, endPage);

        final List<ContentResponse> contentResponses = documentService.fetchImages(fileDto);
        return ResponseEntity.ok().body(DataResponse.from(contentResponses));
    }

    @Operation(summary = "'지식 베이스 만들기' 버튼 클릭 시 파일과 파라미터를 받아서 이미지를 CoreServer 에 전송하고 JobId 반환 API", description = """
              '지식 베이스 만들기' 버튼 클릭 시 PDF 파일과 파라미터를 받아서 이미지를 CoreServer 에 전송 후 JobId(String) 반환
            """)
    @PostMapping(path = "/document/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDocument(
            @RequestPart("project_id") @NotNull Integer projectId,
            @RequestPart("project_folder_id") @NotNull Integer projectFolderId,
            @RequestPart("file") @NotNull MultipartFile file,
            @RequestPart("file_name") @NotNull
            @Size(min = 1, max = 50) String fileName,
            @RequestPart("contents")
            List<@Valid RuleData> contents
    ) {
        //검증 현재는 PDF만 가능
        validator.validateFileSize(file);
        final DocumentJob documentJob = documentService.executeSaveAndTransfer(
                file,
                projectId,
                projectFolderId,
                fileName,
                contents
        );
        documentPartService.saveDocumentPart(documentJob.document(), contents);
        return ResponseEntity.ok().body(documentJob.jobId());
    }
}
