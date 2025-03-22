package com.idt.aio.service;

import com.idt.aio.entity.LanguageModel;
import com.idt.aio.entity.Project;
import com.idt.aio.entity.constant.Feature;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.LanguageModelRepository;
import com.idt.aio.repository.ProjectRepository;
import com.idt.aio.request.LanguageModelRequest;
import com.idt.aio.response.LanguageModelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LanguageModelService {
    private final LanguageModelRepository languageModelRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public List<LanguageModelResponse> getLanguageModelByProjectId(final Integer projectId) {
        final List<LanguageModel> languageModels = languageModelRepository.getLanguageModelByProject_ProjectId(
                projectId);
        return LanguageModelResponse.from(languageModels);
    }

    @Transactional(readOnly = true)
    public LanguageModelResponse getLanguageModelById(final Integer langModelId) {
        final LanguageModel languageModels = languageModelRepository.getLanguageModelByLangModelId(langModelId);
        return LanguageModelResponse.from(languageModels);
    }

    @Transactional
    public void saveLanguageModel(final LanguageModelRequest params) {
        if (!projectRepository.existsById(params.projectId())) {
            throw DomainExceptionCode.PROJECT_NOT_FOUND.newInstance();
        }

        final Project referenceById = projectRepository.getReferenceById(params.projectId());

        languageModelRepository.save(LanguageModel.builder()
                .project(referenceById)
                .name(params.name())
                .vendor(params.vendor())
                .feature(params.feature() == null ? Feature.CHAT : params.feature())
                .apiKey(params.apiKey())
                .build());
    }

    @Transactional
    public void deleteLanguageModelByProjectId(final Integer projectId) {
        languageModelRepository.deleteLanguageModelsByProject_ProjectId(projectId);
    }

    @Transactional
    public void deleteLanguageModelById(final Integer langModelId) {
        languageModelRepository.deleteLanguageModelsByLangModelId(langModelId);
    }
}
