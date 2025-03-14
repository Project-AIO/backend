package com.idt.aio.entity;

import com.idt.aio.entity.constant.Feature;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_lang_model")
public class LanguageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lang_model_id")
    private Long langModelId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "vendor", length = 255)
    private String vendor;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "feature", nullable = false)
    private Feature feature = Feature.CHAT;

    @Column(name = "api_key")
    private String apiKey;
}
