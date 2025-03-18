package com.idt.aio.controller;

import com.idt.aio.request.LanguageModelRequest;
import com.idt.aio.request.ModelPresetRequest;
import com.idt.aio.response.LanguageModelResponse;
import com.idt.aio.response.ModelPresetResponse;
import com.idt.aio.service.LanguageModelService;
import com.idt.aio.service.ModelPresetService;
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
public class ModelPresetController {
    private final ModelPresetService modelPresetService;

    @Operation(summary = "프로젝트 ID로 모델 프리셋 조회 API", description = """
               프로젝트 ID로 모델 프리셋 조회
            """)
    @GetMapping("/model-presets")
    public List<ModelPresetResponse> fetchModelPresetByProjectId(@RequestParam("project_id") final Integer projectId) {
        return modelPresetService.getModelPresetByProjectId(projectId);
    }

    @Operation(summary = "언어모델 ID로 모델 프리셋 조회 API", description = """
               언어모델 ID로 모델 프리셋 조회
            """)
    @GetMapping("/model-preset/lang-model")
    public ModelPresetResponse fetchModelPresetByLangModelId(@RequestParam("lang_model_id") final Integer langModelId) {
        return modelPresetService.getModelPresetByLangModelId(langModelId);
    }

    @Operation(summary = "모델 프리셋 ID로 모델 프리셋 조회 API", description = """
               모델 프리셋 ID로 모델 프리셋 조회
            """)
    @GetMapping("/model-preset")
    public ModelPresetResponse fetchModelPresetByModelId(@RequestParam("model_preset_id") final Integer modelPresetId) {
        return modelPresetService.getModelPresetById(modelPresetId);

    }

    @Operation(summary = "특정 프로젝트의 모델 프리셋 추가 API", description = """
               특정 프로젝트의 모델 프리셋 추가
            """)
    @PostMapping("/model-preset")
    public ResponseEntity<?> saveModelPreset(@ModelAttribute final ModelPresetRequest request) {
        modelPresetService.saveModelPreset(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "특정 프로젝트의 모델 프리셋 삭제 API", description = """
               projectId로 동의어 삭제
            """)
    @DeleteMapping("/model-presets")
    public ResponseEntity<?> deleteModelPresetByProjectId(@RequestParam("project_id") final Integer projectId) {
        modelPresetService.deleteModelPresetByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "모델 프리셋 ID로 모델 프리셋 삭제 API", description = """
               모델 프리셋 ID로 모델 프리셋 삭제
            """)
    @DeleteMapping("/model-presets/{model_preset_id}")
    public ResponseEntity<?> deleteModelPresetById(@PathVariable("model_preset_id") final Integer modelPresetId) {
        modelPresetService.deleteModelPresetById(modelPresetId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
