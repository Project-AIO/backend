package com.idt.aio.dto;

import com.idt.aio.entity.Account;
import com.idt.aio.entity.Project;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectDto {
    private Integer projectId;
    private String name;
    private LocalDateTime createDt;

    public static ProjectDto from(final Project project) {
        return ProjectDto.builder()
                .projectId(project.getProjectId())
                .name(project.getName())
                .createDt(project.getCreateDt())
                .build();
    }

    public static List<ProjectDto> from(List<Project> project) {
        return project.stream()
                .map(p -> ProjectDto.builder()
                        .projectId(p.getProjectId())
                        .name(p.getName())
                        .createDt(p.getCreateDt())
                        .build()
                )
                .toList();
    }

    public Project toEntity() {
        return Project.builder()
                .projectId(this.projectId)
                .name(this.name)
                .build();
    }
}
