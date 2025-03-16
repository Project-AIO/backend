package com.idt.aio.dto;

import com.idt.aio.entity.Document;
import com.idt.aio.entity.constant.State;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DocumentData {
    private Integer docId;
    private Integer projectFolderId;
    private String name;
    private Integer pageCount;
    private State state;
    private String url;
    private Long fileSize;
    private String revision;

    public static List<DocumentDto> from(final List<Document> documents) {
        return documents.stream()
                .map(d -> DocumentDto.builder()
                        .docId(d.getDocId())
                        .projectFolderId(d.getProjectFolder().getProjectFolderId())
                        .name(d.getName())
                        .pageCount(d.getPageCount())
                        .state(d.getState())
                        .url(d.getUrl())
                        .fileSize(d.getFileSize())
                        .revision(d.getRevision())
                        .uploadDt(d.getUploadDt())
                        .build())
                .toList();
    }
}
