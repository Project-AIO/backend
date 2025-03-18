package com.idt.aio.repository;

import com.idt.aio.entity.LanguageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageModelRepository extends JpaRepository<LanguageModel, Integer> {
    List<LanguageModel> getLanguageModelByProject_ProjectId(final Integer projectId);
    LanguageModel getLanguageModelByLangModelId(final Integer langModelId);

    void deleteLanguageModelsByProject_ProjectId(final Integer projectId);

    void deleteLanguageModelsByLangModelId(final Integer langModelId);
}
