package com.idt.aio.entity;

import com.idt.aio.entity.constant.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_doc")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private Long docId;

    @Column(name = "project_folder_id")
    private Long projectFolderId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "page_count")
    private Integer pageCount;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "state")
    private State state = State.PENDING;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "revision", length = 25)
    private String revision;

    @Column(name = "upload_dt")
    private LocalDateTime uploadDt;

    @PrePersist
    private void prePersist() {
        this.uploadDt = LocalDateTime.now();
    }

}
