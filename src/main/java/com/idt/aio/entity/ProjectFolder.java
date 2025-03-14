package com.idt.aio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_project_folder")
public class ProjectFolder extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_folder_id")
    private Long projectFolderId;  // PK

    @Column(name = "project_id")
    private Long projectId;        // 논리적 FK -> tb_project.project_id

    @Column(name = "name", length = 255)
    private String name;

}
