package com.idt.aio.repository;

import com.idt.aio.entity.DocumentPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentPartRepository extends JpaRepository<DocumentPart, Long> {
    List<DocumentPart> findByDocument_DocId(final Integer documentDocId);
}
