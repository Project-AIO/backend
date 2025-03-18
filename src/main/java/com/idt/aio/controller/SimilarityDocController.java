package com.idt.aio.controller;

import com.idt.aio.entity.SimilarityDoc;
import com.idt.aio.request.ConversationRequest;
import com.idt.aio.request.SimilarityDocRequest;
import com.idt.aio.response.ConversationResponse;
import com.idt.aio.response.SimilarityDocResponse;
import com.idt.aio.service.ConversationService;
import com.idt.aio.service.SimilarityDocService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SimilarityDocController {
    private final SimilarityDocService similarityDocService;
    @Operation(summary = "유사도 높은 참조 문서 ID로 대화 조회 API", description = """
               프로젝트 ID로 유사도 높은 참조 문서 조회
            """)
    @GetMapping("/similarity-docs")
    public List<SimilarityDocResponse> fetchSimilarityDocsByProjectId(@RequestParam("doc_id") final Integer docId) {
        return similarityDocService.getSimilarityDocsByDocId(docId);
    }

    @Operation(summary = "유사도 높은 참조 문서 ID로 대화 조회 API", description = """
               언어모델 ID로 언어모델 조회
            """)
    @GetMapping("/similarity-doc")
    public SimilarityDocResponse fetchSimilarityDocByModelId(@RequestParam("similarity_doc_id") final Integer similarityDocId) {
        return similarityDocService.getSimilarityDocById(similarityDocId);

    }

    @Operation(summary = "특정 프로젝트의 유사도 높은 참조 문서 추가 API", description = """
               특정 프로젝트의 유사도 높은 참조 문서 추가
            """)
    @PostMapping("/similarity-doc")
    public ResponseEntity<?> saveSimilarityDoc(@ModelAttribute final SimilarityDocRequest request) {
        similarityDocService.saveSimilarityDoc(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "특정 프로젝트의 유사도 높은 참조 문서 삭제 API", description = """
               projectId로 유사도 높은 참조 문서 삭제
            """)
    @DeleteMapping("/similarity-docs")
    public ResponseEntity<?> deleteSimilarityDocByDocumentId(@RequestParam("doc_id") final Integer docId) {
        similarityDocService.deleteSimilarityDocsByDocumentId(docId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "유사도 높은 참조 문서 ID로 유사도 높은 참조 문서 삭제 API", description = """
               유사도 높은 참조 문서 ID로 유사도 높은 참조 문서 삭제
            """)
    @DeleteMapping("/similarity-docs/{similarity_doc_id}")
    public ResponseEntity<?> deleteSimilarityDocsById(@PathVariable("similarity_doc_id") final Integer similarityDocId) {
        similarityDocService.deleteSimilarityDocsById(similarityDocId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
