package com.idt.aio.dto;

import com.idt.aio.entity.Synonym;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SynonymDto {
    private Integer synonymId;
    private Integer projectId;
    private String source;
    private String match;

    public static SynonymDto from(Synonym synonym) {
        return SynonymDto.builder()
                .synonymId(synonym.getSynonymId())
                .projectId(synonym.getProject().getProjectId())
                .source(synonym.getSource())
                .match(synonym.getMatch())
                .build();
    }

    public static List<SynonymDto> from(List<Synonym> synonym) {
        return synonym.stream().map(SynonymDto::from).toList();
    }
}
