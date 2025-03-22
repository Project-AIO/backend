package com.idt.aio.request;

import com.idt.aio.response.DocumentAccountResponse;
import com.idt.aio.service.AccountService;
import com.idt.aio.service.DocumentAccountService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PermissionController {
    private final DocumentAccountService documentAccountService;
    @Operation(summary = "(미완성) API", description = """
               (미완성) 생성
            """)
    @GetMapping("/accounts/permissions")
    public ResponseEntity<?> getAccountPermissionsProjectId(@RequestParam("project_id") final Integer projectId) {
     //   accountService.getAccountPermissionsProjectId(projectId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //이거 물어보기!!
    @Operation(summary = "파일을 선택한 경우 '적용하기' 버튼 클릭 시 파일에 대한 권한 저장 API", description = """
              파일을 선택한 경우 '적용하기' 버튼 클릭 시 해당 파일에 대해 넘겨진 모든 accounts에 대한 권한을 저장
            """)
    @PostMapping("/doc-account/permissions/documents")
    public ResponseEntity<?> saveDocAccountPermissions(@RequestBody DocAccountRequest docAccountRequest) {
        documentAccountService.bulkSaveDocAccount(docAccountRequest.docId(), docAccountRequest.accountIds());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "document id를 받아 해당 문서 권한이 있는 doc_account 반환 API", description = """
              document id를 받아 해당 문서 권한이 있는 doc_account 반환
            """)
    @GetMapping("/doc-account/permissions/documents")
    public ResponseEntity<List<DocumentAccountResponse>> getDocumentPermissionAccountsByDocId(@RequestParam("doc_id") Integer docId) {
        final List<DocumentAccountResponse> permittedAccountsByDocId = documentAccountService.getPermittedAccountsByDocId(
                docId);
        return ResponseEntity.status(HttpStatus.CREATED).body(permittedAccountsByDocId);
    }
}
