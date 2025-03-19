package com.idt.aio.repository;

import com.idt.aio.entity.SimilarityDoc;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimilarityDocRepository extends JpaRepository<SimilarityDoc, Integer> {

    List<SimilarityDoc> getSimilarityDocsByDocument_DocId(final Integer projectId);

    SimilarityDoc getSimilarityDocsBySimilarityDocId(final Integer similarityDocId);

    void deleteSimilarityDocByDocument_DocId(final Integer docId);
}
