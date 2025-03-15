package com.idt.aio.service;

import com.idt.aio.dto.ProjectDto;
import com.idt.aio.dto.SynonymDto;
import com.idt.aio.entity.Synonym;
import com.idt.aio.repository.SynonymRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SynonymService {
    private final SynonymRepository synonymRepository;
    private final ProjectService projectService;

    @Transactional(readOnly = true)
    public Page<SynonymDto> fetchSynonymsByProjectIdByPage(final Integer projectId, final int page, final int size){
        final Pageable pageable = PageRequest.of(page - 1, size, Sort.by("synonymId").ascending());
        final Page<Synonym> synonymPage = synonymRepository.findByProject_ProjectId(projectId, pageable);
        return synonymPage.map(s->SynonymDto.from(s));
    }

    @Transactional(readOnly = true)
    public List<SynonymDto> fetchSynonymsByProjectId(final Integer projectId){
        final List<Synonym> synonyms = synonymRepository.findByProject_ProjectId(projectId);
        return SynonymDto.from(synonyms);
    }

    @Transactional
    public void saveSynonym(final Integer projectId, final Integer synonymId, final String source, final String match){
        final ProjectDto project = projectService.findProjectById(projectId);

        final Synonym synonym = Synonym.builder()
                .project(project.toEntity())
                .synonymId(synonymId)
                .source(source)
                .match(match)
                .build();

        synonymRepository.save(synonym);
    }
}
