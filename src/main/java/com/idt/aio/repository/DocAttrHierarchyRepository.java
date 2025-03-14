package com.idt.aio.repository;

import com.idt.aio.entity.DocAttrHierarchy;
import com.idt.aio.entity.DocAttrHierarchyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocAttrHierarchyRepository extends JpaRepository<DocAttrHierarchy, DocAttrHierarchyId> {
}
