package com.idt.aio.request;

import com.idt.aio.entity.Project;
import com.idt.aio.entity.ProjectFolder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProjectFolderRequest(
        @NotNull
        @Schema(name = "project_id")
        Integer projectId,
        @Size(max = 255)
        @NotNull
        @Schema(name = "name")
        String name
) {
    public static ProjectFolder from(ProjectFolderRequest request, Project project) {
        return ProjectFolder.builder()
                .name(request.name())
                .project(project)
                .build();
    }
}
