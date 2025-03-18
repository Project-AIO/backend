package com.idt.aio.service;

import com.idt.aio.entity.LanguageModel;
import com.idt.aio.entity.ModelPreset;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.LanguageModelRepository;
import com.idt.aio.repository.ModelPresetRepository;
import com.idt.aio.request.LanguageModelRequest;
import com.idt.aio.request.ModelPresetRequest;
import com.idt.aio.response.LanguageModelResponse;
import com.idt.aio.response.ModelPresetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ModelPresetService {
    private final ModelPresetRepository modelPresetRepository;
    private final LanguageModelRepository languageModelRepository;
    public List<ModelPresetResponse> getModelPresetByProjectId(final Integer projectId) {
        final List<ModelPreset> modelPresets = modelPresetRepository.getModelPresetByLanguageModel_Project_ProjectId(projectId);
        return ModelPresetResponse.from(modelPresets);
    }

    public List<ModelPresetResponse> getModelPresetByLangModelId(final Integer langModelId) {
        final List<ModelPreset> modelPresets = modelPresetRepository.getModelPresetByLanguageModel_LangModelId(langModelId);
        return ModelPresetResponse.from(modelPresets);
    }

    public ModelPresetResponse getModelPresetById(final Integer modelPresetId) {
        final ModelPreset modelPreset = modelPresetRepository.getModelPresetByModelPresetId(modelPresetId);
        return ModelPresetResponse.from(modelPreset);
    }

    public void saveModelPreset(final ModelPresetRequest params) {
        if(!languageModelRepository.existsById(params.langModelId())) {
            throw DomainExceptionCode.LANGUAGE_MODEL_NOT_FOUND.newInstance();
        }

        final LanguageModel referenceById = languageModelRepository.getReferenceById(params.langModelId());

        modelPresetRepository.save(ModelPreset.builder()
                .languageModel(referenceById)
                .temperature(params.temperature() == null ? 1.0f : params.temperature())
                .topP(params.topP() == null ? 1.0f : params.topP())
                .topK(params.topK() == null ? 1 : params.topK())
                .build());
        System.out.println();
    }

    public void deleteModelPresetByProjectId(final Integer projectId) {
        modelPresetRepository.deleteModelPresetsByLanguageModel_Project_ProjectId(projectId);
    }

    public void deleteModelPresetById(final Integer modelPresetId) {
        modelPresetRepository.deleteById(modelPresetId);
    }
}
