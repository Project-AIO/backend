package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_doc_hierarchy")
public class DocumentHierarchy {

    @EmbeddedId
    private DocHierarchyId id;

    @Column(name = "edge")
    private String edge;

    @Column(name = "weight", nullable = false)
    private Integer weight;

}
