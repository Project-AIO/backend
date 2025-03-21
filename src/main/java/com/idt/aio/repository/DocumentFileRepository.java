package com.idt.aio.repository;

import com.idt.aio.entity.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFileRepository extends JpaRepository<DocumentFile, Integer> {
    void deleteByDocument_DocId(final Integer docId);
    @Query("SELECT df.path FROM DocumentFile df WHERE df.document.docId = :docId")
    String findPathByDocument_DocId(final Integer docId);
}
