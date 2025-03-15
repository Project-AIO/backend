package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "tb_conf_knowledge")
public class ConfigurationKnowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conf_knowledge_id")
    private Integer confKnowledgeId;


    //1:n인지 1:1인지 확인 필요
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


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
    private Integer rerktopN = 3;

    @Builder.Default
    @Column(name = "retv_threshold_score", nullable = false)
    private Float retvThreshholdScore = 80f;

    @Builder.Default
    @Column(name = "retv_top_k", nullable = false)
    private Integer retvTopK = 50;

    @Builder.Default
    @Column(name = "keyword_weight", nullable = false)
    private Float keywordWeight = 0f;
}
