package com.idt.aio.service;

import com.idt.aio.entity.LanguageModel;
import com.idt.aio.entity.ModelPreset;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.LanguageModelRepository;
import com.idt.aio.repository.ModelPresetRepository;
import com.idt.aio.request.ModelPresetRequest;
import com.idt.aio.response.ModelPresetResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ModelPresetService {
    private final ModelPresetRepository modelPresetRepository;
    private final LanguageModelRepository languageModelRepository;

    @Transactional(readOnly = true)
    public List<ModelPresetResponse> getModelPresetByProjectId(final Integer projectId) {
        final List<ModelPreset> modelPresets = modelPresetRepository.getModelPresetByLanguageModel_Project_ProjectId(
                projectId);
        return ModelPresetResponse.from(modelPresets);
    }

    @Transactional(readOnly = true)
    public ModelPresetResponse getModelPresetByLangModelId(final Integer langModelId) {
        final ModelPreset modelPresets = modelPresetRepository.getModelPresetByLanguageModel_LangModelId(langModelId);
        return ModelPresetResponse.from(modelPresets);
    }

    @Transactional(readOnly = true)
    public ModelPresetResponse getModelPresetById(final Integer modelPresetId) {
        final ModelPreset modelPreset = modelPresetRepository.getModelPresetByModelPresetId(modelPresetId);
        return ModelPresetResponse.from(modelPreset);
    }

    @Transactional
    public void saveModelPreset(final ModelPresetRequest params) {
        if (!languageModelRepository.existsById(params.langModelId())) {
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

    @Transactional
    public void deleteModelPresetByProjectId(final Integer projectId) {
        modelPresetRepository.deleteModelPresetsByLanguageModel_Project_ProjectId(projectId);
    }

    @Transactional
    public void deleteModelPresetById(final Integer modelPresetId) {
        modelPresetRepository.deleteById(modelPresetId);
    }
}
