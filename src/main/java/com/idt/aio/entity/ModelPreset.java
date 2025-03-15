package com.idt.aio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tb_model_preset")
public class ModelPreset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_preset_id")
    private Integer modelPresetId;

    // 논리적 FK
    @Column(name = "lang_model_id")
    private Integer langModelId;

    @Builder.Default
    @Column(name = "temperature", nullable = false)
    private Float temperature = 1.0f;

    @Builder.Default
    @Column(name = "top_p", nullable = false)
    private Float topP = 1.0f;

    @Builder.Default
    @Column(name = "top_k", nullable = false)
    private Integer topK = 1;
}
