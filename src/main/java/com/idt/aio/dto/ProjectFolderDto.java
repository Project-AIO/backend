package com.idt.aio.dto;

import com.idt.aio.entity.ProjectFolder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFolderDto {
    private Integer projectFolderId;
    private Integer projectId;
    private String name;
    private LocalDateTime createdDt;

    public static List<ProjectFolderDto> from(final List<ProjectFolder> projectFolders) {
        return projectFolders.stream()
                .map(e -> {
                    return ProjectFolderDto.builder()
                            .projectFolderId(e.getProjectFolderId())
                            .projectId(e.getProject().getProjectId())
                            .name(e.getName())
                            .build();
                })
                .toList();
    }
}
