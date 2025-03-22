package com.idt.aio.controller;

import com.idt.aio.response.DocumentAccountResponse;
import com.idt.aio.response.DocumentPartResponse;
import com.idt.aio.service.DocumentAccountService;
import com.idt.aio.service.DocumentPartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DocumentAccountController {
    private final DocumentAccountService documentAccountService;
    @Operation(summary = "문서 ID로 document part 정보 가져오는 API", description = """
               문서 ID로 document part 정보 가져오기
            """)
    @GetMapping("/document-account")
    public List<DocumentAccountResponse> getDocumentsByFolderId(@RequestParam("doc_id") final Integer docId) {
        return documentAccountService.fetchDocAccountsByDocId(docId);
    }

/*    @Operation(summary = "'일괄 적용하기' 버튼 클릭 시 tb_doc_account에 접근 권한 저장 API", description = """
               '일괄 적용하기' 버튼 클릭 시 tb_doc_account에 접근 권한 저장하기
            """)
    @PostMapping("/document-account")
    public List<DocumentAccountResponse> getDocumentsByFolderId(@RequestParam("doc_id") final Integer docId) {
        return documentAccountService.fetchDocAccountsByDocId(docId);
    }*/
}
