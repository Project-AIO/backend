package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_project")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @Column(name = "project_id", length = 100, unique = true)
    private String projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "create_dt")
    private String createDt;

}
