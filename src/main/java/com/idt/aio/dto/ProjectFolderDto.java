package com.idt.aio.dto;

import com.idt.aio.entity.ProjectFolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFolderDto {
    private Long projectFolderId;
    private Long projectId;
    private String name;
    private LocalDateTime createdDt;

    public static List<ProjectFolderDto> from(final List<ProjectFolder> projectFolders){
        return projectFolders.stream()
                .map(e ->{
                    return ProjectFolderDto.builder()
                            .projectFolderId(e.getProjectFolderId())
                            .projectId(e.getProjectId())
                            .name(e.getName())
                            .build();
                })
                .toList();
    }
}
