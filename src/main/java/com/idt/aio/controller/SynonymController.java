package com.idt.aio.controller;

import com.idt.aio.dto.SynonymDto;
import com.idt.aio.request.SynonymPageRequest;
import com.idt.aio.request.SynonymRequest;
import com.idt.aio.request.SynonymUpdateRequest;
import com.idt.aio.service.SynonymService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SynonymController {
    private final SynonymService synonymService;
    @Operation(summary = "프로젝트 귀속 동의어 사전 목록 페이징 조회 API", description = """
           프로젝트 귀속 동의어 사전 목록 페이징 조회 - synonymId로 오름차순 정렬 (수정 가능)
        """)
    @GetMapping("/synonym/page")
    public Page<SynonymDto> getSynonymByPage(@ModelAttribute final SynonymPageRequest request) {

        return synonymService.fetchSynonymsByProjectIdByPage(request.projectId(), request.page(), request.size());
    }

    @Operation(summary = "프로젝트 귀속 동의어 사전 목록 페이징 조회 API", description = """
           프로젝트 귀속 동의어 사전 목록 페이징 조회 - synonymId로 오름차순 정렬 (수정 가능)
        """)
    @GetMapping("/project/{project_id}/synonyms")
    public List<SynonymDto> getSynonym(@PathVariable("project_id") final Integer projectId) {

        return synonymService.fetchSynonymsByProjectId(projectId);
    }

    @Operation(summary = "프로젝트 귀속 동의어 추가 API", description = """
           projectId로 동의어 추가
        """)
    @PostMapping("/synonym")
    public ResponseEntity<?> saveSynonym(@ModelAttribute final SynonymRequest request) {
        synonymService.saveSynonym(request.projectId(), request.synonymId(), request.source(), request.match());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "프로젝트 귀속 동의어 변경 API", description = """
           projectId로 동의어 변경
        """)
    @PatchMapping("/synonym")
    public ResponseEntity<?> updateSynonym(@ModelAttribute final SynonymUpdateRequest request) {
        synonymService.updateSynonymById(request.synonymId(), request.source(), request.match());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "프로젝트 귀속 동의어 삭제 API", description = """
           projectId로 동의어 삭제
        """)
    @DeleteMapping("/synonym")
    public ResponseEntity<?> deleteSynonym(@RequestParam("synonymId") final Integer synonymId) {
        synonymService.deleteSynonymById(synonymId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
