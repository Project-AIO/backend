package com.idt.aio.repository;

import com.idt.aio.entity.Synonym;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynonymRepository extends JpaRepository<Synonym, Integer> {
    Page<Synonym> findByProject_ProjectId(Integer projectId, Pageable pageable);
    List<Synonym> findByProject_ProjectId(Integer projectId);

}
