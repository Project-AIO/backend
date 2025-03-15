package com.idt.aio.entity;

import jakarta.persistence.*;
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
@Table(name = "tb_doc_attr")
public class DocumentAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_attr_id")
    private Integer docAttrId;

    @JoinColumn(name = "doc_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;

    @Column(name = "name", length = 100, nullable = false)
    private String name;
}
