package com.idt.aio.repository;

import com.idt.aio.entity.ProjectAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAccountRepository extends JpaRepository<ProjectAccount, Long> {
}
