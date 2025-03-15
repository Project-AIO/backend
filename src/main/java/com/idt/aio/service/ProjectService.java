package com.idt.aio.service;

import com.idt.aio.dto.ProjectFolderDto;
import com.idt.aio.entity.Project;
import com.idt.aio.entity.ProjectFolder;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.ProjectFolderRepository;
import com.idt.aio.repository.ProjectRepository;
import com.idt.aio.request.ProjectFolderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectFolderRepository projectFolderRepository;
    @Transactional(readOnly = true)
    public List<ProjectFolderDto> fetchProjectFoldersById(final Integer projectId){
        final List<ProjectFolderDto> dto = ProjectFolderDto.from(projectFolderRepository.findAllByProjectProjectId(projectId));

        if(dto.isEmpty()){
            throw DomainExceptionCode.PROJECT_FOLDER_NOT_FOUND.newInstance();
        }

        return dto;
    }

    @Transactional
    public void createProjectFolder(final ProjectFolderRequest request){

        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(DomainExceptionCode.PROJECT_NOT_FOUND::newInstance);

        ProjectFolder entity = ProjectFolderRequest.from(request, project);
        projectFolderRepository.saveAndFlush(entity);
    }
}
