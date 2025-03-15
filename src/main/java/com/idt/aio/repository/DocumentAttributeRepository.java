package com.idt.aio.repository;

import com.idt.aio.entity.DocumentAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentAttributeRepository extends JpaRepository<DocumentAttribute, Integer> {
}
