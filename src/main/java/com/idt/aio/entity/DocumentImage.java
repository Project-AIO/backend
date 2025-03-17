package com.idt.aio.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_doc_image")
public class DocumentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_image_id")
    private Integer docImageId;

    @JoinColumn(name = "doc_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;

    @Column(name = "page", nullable = false)
    private Integer page;

}
