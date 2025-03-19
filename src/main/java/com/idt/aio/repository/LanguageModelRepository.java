package com.idt.aio.repository;

import com.idt.aio.entity.LanguageModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageModelRepository extends JpaRepository<LanguageModel, Integer> {
    List<LanguageModel> getLanguageModelByProject_ProjectId(final Integer projectId);

    LanguageModel getLanguageModelByLangModelId(final Integer langModelId);

    void deleteLanguageModelsByProject_ProjectId(final Integer projectId);

    void deleteLanguageModelsByLangModelId(final Integer langModelId);
}
