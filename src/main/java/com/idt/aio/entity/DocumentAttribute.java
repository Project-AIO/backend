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
@Table(name = "tb_doc_attr")
public class DocumentAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_attr_id")
    private Long docAttrId;

    @Column(name = "doc_id")
    private Long docId;   // 논리적 FK → tb_doc.doc_id

    @Column(name = "name", length = 100, nullable = false)
    private String name;
}
