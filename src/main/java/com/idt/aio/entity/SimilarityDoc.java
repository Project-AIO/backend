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
@Table(name = "tb_similarity_doc")
public class SimilarityDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "similarity_doc_id")
    private Long similarityDocId;

    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "doc_id")
    private Long docId;

    @Column(name = "page", nullable = false)
    private Integer page;

    @Column(name = "score", nullable = false)
    private Float score;
}
