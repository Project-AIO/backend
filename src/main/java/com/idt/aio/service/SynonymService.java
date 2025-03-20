package com.idt.aio.service;

import com.idt.aio.dto.ProjectDto;
import com.idt.aio.dto.SynonymDto;
import com.idt.aio.entity.Synonym;
import com.idt.aio.repository.SynonymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SynonymService {
    private final SynonymRepository synonymRepository;
    private final ProjectService projectService;

    @Transactional(readOnly = true)
    public Page<SynonymDto> fetchSynonymsByProjectIdByPage(final Integer projectId, final int page, final int size,
                                                           final Sort.Direction direction, final String sortProperty) {
        final Pageable pageable = PageRequest.of(page - 1, size, direction, sortProperty);
        final Page<Synonym> synonymPage = synonymRepository.findByProject_ProjectId(projectId, pageable);
        return synonymPage.map(SynonymDto::from);
    }

    @Transactional(readOnly = true)
    public List<SynonymDto> fetchSynonymsByProjectId(final Integer projectId) {
        final List<Synonym> synonyms = synonymRepository.findByProject_ProjectId(projectId);
        return SynonymDto.from(synonyms);
    }

    @Transactional
    public void saveSynonym(final Integer projectId, final String source, final String match) {
        final ProjectDto project = projectService.findProjectById(projectId);

        final Synonym synonym = Synonym.builder()
                .project(project.toEntity())
                .source(source)
                .match(match)
                .build();

        synonymRepository.save(synonym);
    }

    @Transactional
    public void updateSynonymById(final Integer synonymId, final String source, final String match) {
        synonymRepository.updateSynonymById(synonymId, source, match);
    }

    @Transactional
    public void deleteSynonymById(final Integer synonymId) {
        synonymRepository.deleteById(synonymId);
    }
}
