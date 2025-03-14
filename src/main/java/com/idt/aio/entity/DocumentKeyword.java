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
@Table(name = "tb_doc_keyword")
public class DocumentKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_keyword_id")
    private Long docKeywordId;

    @Column(name = "doc_id", nullable = false)
    private Long docId;

    @Column(name = "keyword", length = 20, nullable = false)
    private String keyword;

    @Column(name = "count", nullable = false)
    private Integer count;

}
