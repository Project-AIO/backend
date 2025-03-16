package com.idt.aio.repository;

import com.idt.aio.entity.Document;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByProjectFolder_ProjectFolderId(final Integer projectFolderId);
}
