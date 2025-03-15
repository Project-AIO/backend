package com.idt.aio.repository;

import com.idt.aio.entity.DocumentNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentNodeRepository extends JpaRepository<DocumentNode, Integer> {
}
