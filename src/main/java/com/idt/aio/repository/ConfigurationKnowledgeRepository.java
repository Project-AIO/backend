package com.idt.aio.repository;

import com.idt.aio.entity.ConfigurationKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationKnowledgeRepository extends JpaRepository<ConfigurationKnowledge, Integer> {

}
