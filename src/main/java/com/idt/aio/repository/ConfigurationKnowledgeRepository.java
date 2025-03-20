package com.idt.aio.repository;

import com.idt.aio.dto.ConfigurationKnowledgeDto;
import com.idt.aio.entity.ConfigurationKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationKnowledgeRepository extends JpaRepository<ConfigurationKnowledge, Integer> {
    @Query("SELECT new com.idt.aio.dto.ConfigurationKnowledgeDto(ck.chunkTokenSize, ck.overlapTokenRate, ck.embModelName) FROM ConfigurationKnowledge ck WHERE ck.project.projectId = :projectId")
    Optional<ConfigurationKnowledgeDto> findConfigKnowledgeByProjectId(final Integer projectId);
}
