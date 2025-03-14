package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "tb_doc_node")
public class DocumentNode {

    @Id
    @Column(name = "doc_node_id")
    private Long docNodeId;

    @Column(name = "doc_attr_id", nullable = false)
    private Long docAttrId;



}
