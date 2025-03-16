package com.idt.aio.controller;

import com.idt.aio.dto.DocumentDto;
import com.idt.aio.dto.FeedbackDto;
import com.idt.aio.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DocumentController {
    private final DocumentService documentService;

    @Operation(summary = "프로젝트 폴더 ID로 문서 가져오는 API", description = """
           프로젝트 ID로 피드백 가져오기
        """)
    @GetMapping("folders/{project_folder_id}/feedbacks")
    public List<DocumentDto> getDocumentsByFolderId(@PathVariable("project_folder_id") final Integer folderId) {
        return documentService.fetchDocumentByFolderId(folderId);
    }
}
