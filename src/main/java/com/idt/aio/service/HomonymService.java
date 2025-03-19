package com.idt.aio.service;

import com.idt.aio.dto.HomonymDto;
import com.idt.aio.dto.ProjectDto;
import com.idt.aio.entity.Homonym;
import com.idt.aio.repository.HomonymRepository;
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
public class HomonymService {
    private final HomonymRepository homonymRepository;
    private final ProjectService projectService;

    @Transactional(readOnly = true)
    public Page<HomonymDto> fetchHomonymByProjectIdByPage(final Integer projectId, final int page, final int size,
                                                          final Sort.Direction direction, final String sortProperty) {
        final Pageable pageable = PageRequest.of(page - 1, size, direction, sortProperty);
        final Page<Homonym> homonymPage = homonymRepository.findByProject_ProjectId(projectId, pageable);
        return homonymPage.map(s -> HomonymDto.from(s));
    }

    @Transactional(readOnly = true)
    public List<HomonymDto> fetchHomonymsByProjectId(final Integer projectId) {
        final List<Homonym> homonym = homonymRepository.findByProject_ProjectId(projectId);
        return HomonymDto.from(homonym);
    }

    @Transactional
    public void saveHomonym(final Integer projectId, final String source, final String match) {
        final ProjectDto project = projectService.findProjectById(projectId);

        final Homonym homonym = Homonym.builder()
                .project(project.toEntity())
                .source(source)
                .match(match)
                .build();

        homonymRepository.save(homonym);
    }

    @Transactional
    public void updateHomonymById(final Integer homonym, final String source, final String match) {
        homonymRepository.updateHomonymById(homonym, source, match);
    }

    @Transactional
    public void deleteHomonymById(final Integer homonym) {
        homonymRepository.deleteById(homonym);
    }
}
