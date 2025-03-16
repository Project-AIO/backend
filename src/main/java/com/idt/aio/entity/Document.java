package com.idt.aio.entity;

import com.idt.aio.dto.DocumentData;
import com.idt.aio.dto.DocumentDto;
import com.idt.aio.entity.constant.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
@Table(name = "tb_doc")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private Integer docId;

    @JoinColumn(name = "project_folder_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectFolder projectFolder;

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

    public static Document from(final DocumentData documentData){
        return Document.builder()
                .docId(documentData.getDocId())
                .projectFolder(ProjectFolder.builder().projectFolderId(documentData.getProjectFolderId()).build())
                .name(documentData.getName())
                .pageCount(documentData.getPageCount())
                .state(documentData.getState())
                .url(documentData.getUrl())
                .fileSize(documentData.getFileSize())
                .revision(documentData.getRevision())
                .build();
    }

}
