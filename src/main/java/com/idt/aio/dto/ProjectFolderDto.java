package com.idt.aio.dto;

import com.idt.aio.entity.ProjectFolder;
import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectFolderDto {
    private Integer projectFolderId;
    private Integer projectId;
    private String name;
    private LocalDateTime createdDt;

    public static List<ProjectFolderDto> from(final List<ProjectFolder> projectFolders) {
        return projectFolders.stream()
                .map(e -> ProjectFolderDto.builder()
                        .projectFolderId(e.getProjectFolderId())
                        .projectId(e.getProject().getProjectId())
                        .name(e.getName())
                        .createdDt(e.getCreateDt())
                        .build()
                )
                .toList();
    }
}
