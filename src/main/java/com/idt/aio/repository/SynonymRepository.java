package com.idt.aio.repository;

import com.idt.aio.entity.Synonym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynonymRepository extends JpaRepository<Synonym, Long> {
}
