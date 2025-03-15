package com.idt.aio.repository;

import com.idt.aio.entity.ModelPreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelPresetRepository extends JpaRepository<ModelPreset, Integer> {
}
