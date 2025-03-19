package com.idt.aio.dto;

import com.idt.aio.entity.Homonym;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomonymDto {
    private Integer homonymId;
    private Integer projectId;
    private String source;
    private String match;

    public static HomonymDto from(Homonym homonym) {
        return HomonymDto.builder()
                .homonymId(homonym.getHomonymId())
                .projectId(homonym.getProject().getProjectId())
                .source(homonym.getSource())
                .match(homonym.getMatch())
                .build();
    }

    public static List<HomonymDto> from(List<Homonym> homonym) {
        return homonym.stream().map(HomonymDto::from).toList();
    }
}
