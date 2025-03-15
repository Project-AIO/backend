package com.idt.aio.repository;

import com.idt.aio.entity.DocumentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentAccountRepository extends JpaRepository<DocumentAccount, Integer> {
}
