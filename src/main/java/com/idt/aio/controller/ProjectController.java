package com.idt.aio.controller;

import com.idt.aio.dto.ProjectFolderDto;
import com.idt.aio.request.ProjectFolderRequest;
import com.idt.aio.request.ProjectRequest;
import com.idt.aio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProjectController {

    private final ProjectService projectService;


    @Operation(summary = "프로젝트 조회 API", description = """
               프로젝트 조회
            """)
    @GetMapping("/projects")
    public ResponseEntity<?> getProjectList() {
        return ResponseEntity.ok(projectService.getProjectList());
    }

    @Operation(summary = "프로젝트 생성 API", description = """
               프로젝트 생성
            """)
    @PostMapping("/project")
    public ResponseEntity<?> addProject(@RequestBody @Valid final ProjectRequest request) {

        projectService.createProject(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "프로젝트 ID로 프로젝트 삭제 API", description = """
                실제 프로젝트 폴더, 문서 등 자식 관계 데이터들도 다 Cascade 됨
            """)
    @DeleteMapping("/project-folders/{project_folder_id}")
    public ResponseEntity<?> removeProjectFolder(@PathVariable("project_folder_id") final Integer folderId,
                                                 @RequestParam("project_id") final Integer projectId) {
        projectService.deleteProjectFolderById(folderId, projectId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "프로젝트 ID로 프로젝트 삭제 API", description = """
                실제 프로젝트 폴더, 문서 등 자식 관계 데이터들도 다 Cascade 됨
            """)
    @DeleteMapping("/projects/{project_id}")
    public ResponseEntity<?> removeProject(@PathVariable("project_id") final Integer projectId) {
        projectService.deleteProjectById(projectId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "프로젝트 ID로 프로젝트 폴더 정보 가져오는 API", description = """
               프로젝트 ID로 프로젝트 폴더 정보 가져오기
            """)
    @GetMapping("/project_folders")
    public List<ProjectFolderDto> getProjectFolder(@RequestParam("project_id") final Integer project_id) {
        return projectService.fetchProjectFoldersById(project_id);
    }

    @Operation(summary = "프로젝트 프로젝트 폴더 생성 API", description = """
               프로젝트 프로젝트 폴더 생성
            """)
    @PostMapping("/project_folder")
    public ResponseEntity<?> addProjectFolder(@RequestBody @Valid final ProjectFolderRequest request) {

        projectService.createProjectFolder(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
