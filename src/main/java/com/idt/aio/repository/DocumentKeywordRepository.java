package com.idt.aio.repository;

import com.idt.aio.entity.DocumentKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentKeywordRepository extends JpaRepository<DocumentKeyword, Long> {
}
