package com.idt.aio.data.domain;

import com.idt.aio.entity.Project;

import java.util.List;
import java.util.stream.IntStream;

public class ProjectFixture {
    public static List<Project> createProjects(final int count) {
        return IntStream.range(0, count).mapToObj(i -> Project.builder()
                .name("name" + i)
                .build()).toList();
    }
}
