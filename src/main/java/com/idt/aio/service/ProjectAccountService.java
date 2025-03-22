package com.idt.aio.service;

import com.idt.aio.dto.ProjectAccountDto;
import com.idt.aio.dto.ProjectFolderDto;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.exception.DuplicateMemberException;
import com.idt.aio.repository.ProjectAccountRepository;
import com.idt.aio.request.ProjectAccountRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProjectAccountService {

    @Autowired
    ProjectAccountRepository projectAccountRepository;

    @Transactional (readOnly = true)
    public List<ProjectAccountDto> getProjectAccountList(Integer projectId) {

        final List<ProjectAccountDto> projectAccountDto = ProjectAccountDto.from(
                projectAccountRepository.findByProject_ProjectId(projectId));

        /*if (projectAccountDto.isEmpty()) {
            throw DomainExceptionCode.****.newInstance();
        }*/

        return projectAccountDto;
    }

    @Transactional
    public ResponseEntity<?> createProjectAccountList(ProjectAccountRequest request) {

        Integer projectId = request.projectId();
        List<String> accountList = request.accountList();

        for(String accountId : accountList) {
            projectAccountRepository.saveProjectAccount(projectId, accountId);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public ResponseEntity<?> delProjectAccountList(ProjectAccountRequest request) {

        Integer projectId = request.projectId();
        List<String> accountList = request.accountList();

        for(String accountId : accountList) {
            projectAccountRepository.deleteByProjectIdAccountId(projectId, accountId) ;
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
