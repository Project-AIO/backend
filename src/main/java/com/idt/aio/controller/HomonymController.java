package com.idt.aio.controller;

import com.idt.aio.dto.HomonymDto;
import com.idt.aio.request.HomonymPageRequest;
import com.idt.aio.request.HomonymRequest;
import com.idt.aio.request.HomonymUpdateRequest;
import com.idt.aio.service.HomonymService;
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
public class HomonymController {
    private final HomonymService homonymService;

    @Operation(summary = "프로젝트 귀속 이의어 사전 목록 페이징 조회 API", description = """
               프로젝트 귀속 이의어 사전 목록 페이징 조회 - homonymId 오름차순 정렬 (수정 가능)
            """)
    @GetMapping("/homonym/page")
    public Page<HomonymDto> getHomonymByPage(@ModelAttribute final HomonymPageRequest request) {

        return homonymService.fetchHomonymByProjectIdByPage(request.projectId(), request.page(), request.size());
    }

    @Operation(summary = "프로젝트 귀속 이의어 사전 목록 전체 조회 API", description = """
               프로젝트 귀속 이의어 사전 목록 전체 조회
            """)
    @GetMapping("/project/{project_id}/homonyms")
    public List<HomonymDto> getHomonym(@PathVariable("project_id") final Integer projectId) {

        return homonymService.fetchHomonymsByProjectId(projectId);
    }

    @Operation(summary = "프로젝트 귀속 이의어 추가 API", description = """
               projectId로 이의어 추가
            """)
    @PostMapping("/homonym")
    public ResponseEntity<?> saveHomonym(@ModelAttribute final HomonymRequest request) {
        homonymService.saveHomonym(request.projectId(), request.homonymId(), request.source(), request.match());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "프로젝트 귀속 이의어 변경 API", description = """
               projectId로 이의어 변경
            """)
    @PatchMapping("/homonym")
    public ResponseEntity<?> updateHomonym(@ModelAttribute final HomonymUpdateRequest request) {
        homonymService.updateHomonymById(request.homonymId(), request.source(), request.match());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "프로젝트 귀속 이의어 삭제 API", description = """
               projectId로 이의어 삭제
            """)
    @DeleteMapping("/homonym")
    public ResponseEntity<?> deleteHomonym(@RequestParam("homonymId") final Integer homonymId) {
        homonymService.deleteHomonymById(homonymId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
