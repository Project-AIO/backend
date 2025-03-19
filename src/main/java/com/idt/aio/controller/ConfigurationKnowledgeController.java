package com.idt.aio.controller;

import com.idt.aio.request.ConfigKnowledgeRequest;
import com.idt.aio.request.ConfigKnowledgeUpdateRequest;
import com.idt.aio.service.ConfigurationKnowledgeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ConfigurationKnowledgeController {
    private final ConfigurationKnowledgeService service;

    @Operation(summary = "Knowledge Configuration 생성 API", description = """
               Knowledge Configuration 생성
            """)
    @PostMapping("/configuration-knowledge")
    public ResponseEntity<?> addConfigurationKnowledge(@RequestBody @Valid final ConfigKnowledgeRequest request) {
        service.createConfigKnowledge(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Knowledge Configuration 수정 API", description = """
               Knowledge Configuration 수정
            """)
    @PatchMapping("/configuration-knowledges/{confKnowledge_id}")
    public ResponseEntity<?> updateConfigurationKnowledge(@PathVariable("confKnowledge_id") Integer confKnowledgeId,
                                                          @RequestBody final ConfigKnowledgeUpdateRequest request) {
        service.updateConfigKnowledge(confKnowledgeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
