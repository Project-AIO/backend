package com.idt.aio.controller;

import com.idt.aio.dto.SynonymDto;
import com.idt.aio.request.LanguageModelRequest;
import com.idt.aio.response.LanguageModelResponse;
import com.idt.aio.service.LanguageModelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LanguageModelController {
    private final LanguageModelService languageModelService;

    @Operation(summary = "프로젝트 ID로 언어모델 조회 API", description = """
               프로젝트 ID로 언어모델 조회
            """)
    @GetMapping("/language-models")
    public List<LanguageModelResponse> fetchLanguageModelByProjectId(@RequestParam("project_id") final Integer projectId) {
        return languageModelService.getLanguageModelByProjectId(projectId);
    }

    @Operation(summary = "언어모델 ID로 언어모델 조회 API", description = """
               언어모델 ID로 언어모델 조회
            """)
    @GetMapping("/language-model")
    public List<LanguageModelResponse> fetchLanguageModelByModelId(@RequestParam("lang_model_id") final Integer langModelId) {
        return languageModelService.getLanguageModelById(langModelId);

    }

    @Operation(summary = "특정 프로젝트의 언어모델 추가 API", description = """
               특정 프로젝트의 언어모델 추가
            """)
    @PostMapping("/language-model")
    public ResponseEntity<?> saveLanguageModel(@ModelAttribute final LanguageModelRequest request) {
        languageModelService.saveLanguageModel(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "특정 프로젝트의 언어모델 삭제 API", description = """
               projectId로 동의어 삭제
            """)
    @DeleteMapping("/language-model")
    public ResponseEntity<?> deleteLanguageModelByProjectId(@RequestParam("project_id") final Integer projectId) {
        languageModelService.deleteLanguageModelByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "언어모델 ID로 언어모델 삭제 API", description = """
               언어모델 ID로 언어모델 삭제
            """)
    @DeleteMapping("/language-model/{lang_model_id}")
    public ResponseEntity<?> deleteLanguageModelById(@PathVariable("lang_model_id") final Integer langModelId) {
        languageModelService.deleteLanguageModelById(langModelId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
