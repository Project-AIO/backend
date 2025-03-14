package com.idt.aio.repository;

import com.idt.aio.entity.LanguageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageModelRepository extends JpaRepository<LanguageModel, Long> {
}
