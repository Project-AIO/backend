package com.idt.aio.repository;

import com.idt.aio.entity.SimilarityDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimilarityDocRepository extends JpaRepository<SimilarityDoc, Integer> {

}
