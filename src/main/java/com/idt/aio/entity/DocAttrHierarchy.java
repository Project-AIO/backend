package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_doc_attr_hierarchy")
public class DocAttrHierarchy {
    @EmbeddedId
    private DocAttrHierarchyId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ancestorId")
    @JoinColumn(name = "ancestor_id")
    private DocumentAttribute ancestor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("descendantId")
    @JoinColumn(name = "descendant_id")
    private DocumentAttribute descendant;

    @NotNull
    @Column(name = "depth")
    private Integer depth;
}
