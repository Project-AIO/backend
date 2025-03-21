package com.idt.aio.controller;


import com.idt.aio.dto.DocumentDto;
import com.idt.aio.response.DocumentPartResponse;
import com.idt.aio.service.DocumentPartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DocumentPartController {
    private final DocumentPartService documentPartService;
    @Operation(summary = "문서 ID로 document part 정보 가져오는 API", description = """
               문서 ID로 document part 정보 가져오기
            """)
    @GetMapping("/document-parts")
    public List<DocumentPartResponse> getDocumentsByFolderId(@RequestParam("doc_id") final Integer docId) {
        return documentPartService.getDocumentPartsByDocumentId(docId);
    }
}
