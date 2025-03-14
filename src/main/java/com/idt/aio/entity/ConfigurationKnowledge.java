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
@Table(name = "tb_conf_knowledge")
public class ConfigurationKnowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conf_knowledge_id")
    private Long confKnowledgeId;


    @Column(name = "project_id")
    private Long projectId;


    @Column(name = "chk_token_size", nullable = false)
    private String chunkTokenSize;

    @Column(name = "overlap_token_rate", nullable = false)
    private Float overlapTokenRate;

    @Column(name = "emb_model_name", nullable = false, length = 255)
    private String embModelName;

    @Column(name = "rerk_model_name", nullable = false, length = 255)
    private String rerkModelName;

    @Builder.Default
    @Column(name = "rerk_top_n", nullable = false)
    private Integer rerktopN=3;

    @Builder.Default
    @Column(name = "retv_threshold_score", nullable = false)
    private Float retvThreshholdScore=80f;

    @Builder.Default
    @Column(name = "retv_top_k", nullable = false)
    private Integer retvTopK=50;

    @Builder.Default
    @Column(name = "keyword_weight", nullable = false)
    private Float keywordWeight =0f;
}
