package com.idt.aio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ConfigurationKnowledgeDto {
    private String chunkSize;
    private Float overlapTokenRate;
    private String embModelName;
}
