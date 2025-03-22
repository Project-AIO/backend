package com.idt.aio.repository;

import com.idt.aio.entity.ProjectAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectAccountRepository extends JpaRepository<ProjectAccount, Integer> {

    List<ProjectAccount> findByProject_ProjectId(Integer projectId);

    @Modifying
    @Query(value = "insert into tb_project_account (project_id, account_id) values (:projectId, :accountId)", nativeQuery = true)
    void saveProjectAccount(@Param("projectId") final Integer projectId, @Param("accountId") final String accountId);

    @Modifying
    @Query(value = "delete from tb_project_account p where p.project_id = :projectId and p.account_id = :accountId", nativeQuery = true)
    void deleteByProjectIdAccountId(@Param("projectId") final Integer projectId, @Param("accountId") final String accountId);

}
