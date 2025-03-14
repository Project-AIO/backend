package com.idt.aio.repository;

import com.idt.aio.entity.DocumentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentImageRepository extends JpaRepository<DocumentImage, Long> {
}
