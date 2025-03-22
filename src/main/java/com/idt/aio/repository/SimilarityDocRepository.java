package com.idt.aio.repository;

import com.idt.aio.entity.SimilarityDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimilarityDocRepository extends JpaRepository<SimilarityDoc, Integer> {

    List<SimilarityDoc> getSimilarityDocsByDocument_DocId(final Integer projectId);

    SimilarityDoc getSimilarityDocsBySimilarityDocId(final Integer similarityDocId);

    void deleteSimilarityDocByDocument_DocId(final Integer docId);
}
