package com.idt.aio.entity;

import com.idt.aio.entity.constant.Extension;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_doc_file")
public class DocumentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_file_id")
    private Integer docFileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id")
    private Document document;
    @Column(name = "extension", nullable = false, length = 10)
    private String extension;
    @Column(name = "path", nullable = false, length = 255)
    private String path;

    @Column(name = "file_name", nullable = false, length = 50)
    private String fileName;
    @Column(name = "original_file_name", nullable = false, length = 60)
    private String originalFileName;
    @Column(name = "total_page", nullable = false)
    private int totalPage;
    @Column(name="file_size", nullable = false)
    private long fileSize;

    @Column(name = "revision")
    private String revision;
}
