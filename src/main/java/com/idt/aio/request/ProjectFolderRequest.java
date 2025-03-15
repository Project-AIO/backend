package com.idt.aio.request;

import com.idt.aio.entity.Project;
import com.idt.aio.entity.ProjectFolder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProjectFolderRequest(
        @NotNull
        Integer projectId,
        @Size(max = 255)
        @NotNull
        String name
) {
    public static ProjectFolder from(ProjectFolderRequest request, Project project) {
        return ProjectFolder.builder()
                .name(request.name())
                .project(project)
                .build();
    }
}
