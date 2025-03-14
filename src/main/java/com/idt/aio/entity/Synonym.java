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
@Table(name = "tb_synonym")
public class Synonym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "synonym_id")
    private Long synonymId;

    @Column(name = "project_id")
    private Long projectId;


    @Column(name = "source", nullable = false, length = 25)
    private String source;


    @Column(name = "match", nullable = false, length = 25)
    private String match;
}
