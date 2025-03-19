package com.idt.aio.repository;

import com.idt.aio.entity.ModelPreset;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelPresetRepository extends JpaRepository<ModelPreset, Integer> {
    List<ModelPreset> getModelPresetByLanguageModel_Project_ProjectId(final Integer projectId);

    ModelPreset getModelPresetByLanguageModel_LangModelId(final Integer langModelId);

    ModelPreset getModelPresetByModelPresetId(final Integer modelPresetId);

    void deleteModelPresetsByLanguageModel_Project_ProjectId(final Integer projectId);
}
