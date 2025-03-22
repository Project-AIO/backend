package com.idt.aio.repository;

import com.idt.aio.dto.ProjectDto;
import com.idt.aio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Modifying
    @Query(value = "select p.project_id, p.name, p.create_dt from tb_project p", nativeQuery = true)
    List<Project> findProjcet();

}
