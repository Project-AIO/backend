package com.idt.aio.repository;

import com.idt.aio.dto.DocumentRelatedIds;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.constant.State;
import com.idt.aio.repository.custom.CustomDocumentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer>, CustomDocumentRepository {
    List<Document> findByProjectFolder_ProjectFolderId(final Integer projectFolderId);

    @Modifying
    @Query("update Document d set d.state = :state where d.docId in :docIds")
    void updateStats(@Param("docIds") final Integer docIds, @Param("state") final State state);
}
