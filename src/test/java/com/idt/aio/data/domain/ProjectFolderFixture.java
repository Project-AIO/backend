package com.idt.aio.data.domain;

import com.idt.aio.entity.Project;
import com.idt.aio.entity.ProjectFolder;

import java.util.List;
import java.util.stream.IntStream;

public class ProjectFolderFixture {
    public static List<ProjectFolder> createProjectFolders(final int count, final List<Project> projects) {
        return IntStream.range(0, count).mapToObj(i -> ProjectFolder.builder()
                .name("name" + i)
                .project(projects.get(i))
                .build()).toList();
    }
}
