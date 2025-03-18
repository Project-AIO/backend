package com.idt.aio.service;

import com.idt.aio.entity.ConfigurationKnowledge;
import com.idt.aio.entity.Project;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.ConfigurationKnowledgeRepository;
import com.idt.aio.repository.ProjectRepository;
import com.idt.aio.request.ConfigKnowledgeRequest;
import com.idt.aio.request.ConfigKnowledgeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConfigurationKnowledgeService {
    private final ConfigurationKnowledgeRepository repository;
    private final ProjectRepository projectRepository;
    @Transactional
    public void createConfigKnowledge(final ConfigKnowledgeRequest params) {

        if(!projectRepository.existsById(params.projectId())){
            throw DomainExceptionCode.PROJECT_NOT_FOUND.newInstance();
        }

        final Project referenceById = projectRepository.getReferenceById(params.projectId());

        final ConfigurationKnowledge entity = ConfigurationKnowledge.of(
                referenceById,
                params.chunkTokenSize(),
                params.overlapTokenRate(),
                params.embModelName(),
                params.rerkModelName(),
                params.rerktopN(),
                params.retvThreshholdScore(),
                params.retvTopK(),
                params.keywordWeight()
        );

        repository.save(entity);

    }

    @Transactional
    public void updateConfigKnowledge(final Integer confKnowledgeId, final ConfigKnowledgeUpdateRequest params){
        final ConfigurationKnowledge entity = repository.findById(confKnowledgeId)
                .orElseThrow(DomainExceptionCode.CONFIGURATION_KNOWLEDGE_NOT_FOUND::newInstance);

        entity.update(params.chunkTokenSize(),
                params.overlapTokenRate(),
                params.embModelName(),
                params.rerkModelName(),
                params.rerktopN(),
                params.retvThreshholdScore(),
                params.retvTopK(),
                params.keywordWeight());
    }
}
