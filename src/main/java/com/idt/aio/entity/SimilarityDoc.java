package com.idt.aio.entity;
import jakarta.persistence.*;
import javax.print.Doc;
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
@Table(name = "tb_similarity_doc")
public class SimilarityDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "similarity_doc_id")
    private Integer similarityDocId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    //1:다 인거 같은데 확인 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id")
    private Document document;

    @Column(name = "page", nullable = false)
    private Integer page;

    @Column(name = "score", nullable = false)
    private Float score;
}
