package com.idt.aio.controller;

import com.idt.aio.dto.LoginDto;
import com.idt.aio.dto.ProjectDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProjectController {

    /*
        프로젝트목록조회
     */
    @GetMapping("/api/v1/project")
    public ResponseEntity getProject(@Valid @RequestBody ProjectDto projectDto)  {
        return null;
    }

}
