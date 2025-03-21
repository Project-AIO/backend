package com.idt.aio.repository.custom;

import com.idt.aio.response.DocumentData;

import java.util.List;
import java.util.Optional;

public interface CustomDocumentRepository {
    List<DocumentData> findDocumentDataByProjectId(final Integer projectId);
    Optional<DocumentData> findDocumentDataById(final Integer docId);
}
