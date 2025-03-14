package com.idt.aio.service;

import com.idt.aio.dto.ProjectFolderDto;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.ProjectFolderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectFolderRepository projectFolderRepository;
    @Transactional(readOnly = true)
    public List<ProjectFolderDto> fetchProjectFoldersById(final Long projectId){
        final List<ProjectFolderDto> dto = ProjectFolderDto.from(projectFolderRepository.findAllByProjectId(projectId));

        if(dto.isEmpty()){
            throw DomainExceptionCode.PROJECT_FOLDER_NOT_FOUND.newInstance();
        }

        return dto;
    }
}
