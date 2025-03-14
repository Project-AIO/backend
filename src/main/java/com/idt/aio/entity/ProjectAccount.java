package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_project_account")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAccount {

    @Id
    @Column(name = "project_account_id", length = 100, unique = true)
    private String projectAccountId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "account_id")
    private String accountId;

}