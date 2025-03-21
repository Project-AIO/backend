package com.idt.aio.repository;

import com.idt.aio.entity.DocumentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentAccountRepository extends JpaRepository<DocumentAccount, Integer> {
    List<DocumentAccount> findDocumentAccountByDocument_DocId(final Integer docId);
}
