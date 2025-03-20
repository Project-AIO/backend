package com.idt.aio.data.domain;

import com.idt.aio.entity.ConfigurationKnowledge;
import com.idt.aio.entity.Project;

import java.util.List;

public class ConfigurationKnowledgeFixture {
    static public List<ConfigurationKnowledge> createConfigurationKnowledges(final List<Project> projects, final int count) {
        return projects.stream().map(project -> {
            return ConfigurationKnowledge.builder()
                    .project(project)
                    .chunkTokenSize("chunkTokenSize")
                    .overlapTokenRate(1.0f)
                    .embModelName("embModelName")
                    .rerkModelName("rerkModelName")
                    .rerktopN(1)
                    .retvThresholdScore(1.0f)
                    .retvTopK(1)
                    .keywordWeight(1.0f)
                    .build();
        }).toList();
    }
}
