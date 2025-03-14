package com.idt.aio.repository;

import com.idt.aio.entity.DocumentPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentPartRepository extends JpaRepository<DocumentPart, Long> {
}
