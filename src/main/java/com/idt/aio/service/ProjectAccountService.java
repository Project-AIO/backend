package com.idt.aio.service;

import com.idt.aio.dto.ProjectAccountData;
import com.idt.aio.repository.ProjectAccountRepository;
import com.idt.aio.request.ProjectAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProjectAccountService {

    @Autowired
    ProjectAccountRepository projectAccountRepository;

    @Transactional (readOnly = true)
    public List<ProjectAccountData> getProjectAccountList(Integer projectId) {

        final List<ProjectAccountData> projectAccountData = ProjectAccountData.from(
                projectAccountRepository.findByProject_ProjectId(projectId));

        /*if (projectAccountData.isEmpty()) {
            throw DomainExceptionCode.****.newInstance();
        }*/

        return projectAccountData;
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
