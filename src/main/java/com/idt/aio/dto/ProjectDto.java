package com.idt.aio.dto;

import com.idt.aio.entity.Project;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectDto {
    private Integer projectId;
    private String name;

    public static ProjectDto from(final Project project) {
        return ProjectDto.builder()
                .projectId(project.getProjectId())
                .name(project.getName())
                .build();
    }

    public Project toEntity() {
        return Project.builder()
                .projectId(this.projectId)
                .name(this.name)
                .build();
    }
}
