package com.idt.aio.repository;

import com.idt.aio.entity.DocHierarchyId;
import com.idt.aio.entity.DocumentHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentHierarchyRepository extends JpaRepository<DocumentHierarchy, DocHierarchyId> {
}
