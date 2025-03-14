package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_doc_attr_hierarchy")
public class DocAttrHierarchy {
    @EmbeddedId
    private DocAttrHierarchyId id;

    @NotNull
    @Column(name = "depth")
    private Integer depth;
}
