package com.idt.aio.repository;

import com.idt.aio.entity.ProjectFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectFolderRepository extends JpaRepository<ProjectFolder, Integer> {
    List<ProjectFolder> findAllByProjectProjectId(final Integer projectId);

}
