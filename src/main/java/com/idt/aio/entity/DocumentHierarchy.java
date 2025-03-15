package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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
@Table(name = "tb_doc_hierarchy")
public class DocumentHierarchy {

    @EmbeddedId
    private DocHierarchyId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("fromNodeId")
    @JoinColumn(name = "from_node_id")
    private DocumentNode fromNodeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("toNodeId")
    @JoinColumn(name = "to_node_id")
    private DocumentNode toNodeId ;

    @Column(name = "edge")
    private String edge;

    @Column(name = "weight", nullable = false)
    private Integer weight;

}
