package com.idt.aio.controller;

import com.idt.aio.dto.LoginDto;
import com.idt.aio.dto.ProjectDto;
import com.idt.aio.dto.ProjectFolderDto;
import com.idt.aio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProjectController {
    private final ProjectService projectService;
    /*
        프로젝트목록조회
     */
    @GetMapping("/api/v1/project")
    public ResponseEntity getProject(@Valid @RequestBody ProjectDto projectDto)  {
        return null;
    }


    @Operation(summary = "프로젝트 ID로 프로젝트 폴더 정보 가져오는 API", description = """
           프로젝트 ID로 프로젝트 폴더 정보 가져오기
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/project_folder")
    public List<ProjectFolderDto> getProjectFolder(@RequestParam("project_id") final Integer project_id) {
        return projectService.fetchProjectFoldersById(project_id);
    }
}
