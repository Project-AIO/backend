package com.idt.aio.controller;

import com.idt.aio.dto.ProjectAccountDto;
import com.idt.aio.request.ProjectAccountRequest;
import com.idt.aio.service.ProjectAccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProjectAccountController {

    @Autowired
    private final ProjectAccountService projectAccountService;

    public ProjectAccountController(ProjectAccountService projectAccountService) {
        this.projectAccountService = projectAccountService;
    }

    @Operation(summary = "프로젝트 귀속 계정 목록 조회 API", description = """
               프로젝트에 귀속된 계정 목록을 조회
            """)
    @GetMapping("/v1/project-accounts")
    public List<ProjectAccountDto> getProjectAccountList(@RequestParam("project_id") Integer project_id) {
        return projectAccountService.getProjectAccountList(project_id);
    }

    @Operation(summary = "프로젝트 귀속 계정 추가 API", description = """
               프로젝트에 귀속 계정을 추가
            """)
    @PostMapping("/v1/project-account")
    public ResponseEntity<?> createProjectAccountList(@RequestBody @Valid final ProjectAccountRequest request) {
        projectAccountService.createProjectAccountList(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "프로젝트 귀속 계정 삭제 API", description = """
               프로젝트에 귀속된 계정을 삭제
            """)
    @DeleteMapping("/v1/project-account")
    public void delProjectAccountList(@RequestBody @Valid final ProjectAccountRequest request) {
        projectAccountService.delProjectAccountList(request);
    }

}
