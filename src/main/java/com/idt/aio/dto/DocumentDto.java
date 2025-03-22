package com.idt.aio.dto;

import com.idt.aio.entity.Document;
import com.idt.aio.entity.constant.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class DocumentDto {
    private Integer docId;
    private Integer projectFolderId;
    private String name;
    private State state;
    private LocalDateTime uploadDt;

    public static DocumentDto from(Document document) {
        return DocumentDto.builder()
                .docId(document.getDocId())
                .projectFolderId(document.getProjectFolder().getProjectFolderId())
                .name(document.getName())
                .state(document.getState())
                .uploadDt(document.getUploadDt())
                .build();
    }
    public static List<DocumentDto> from(final List<Document> documents) {
        return documents.stream()
                .map(DocumentDto::from)
                .toList();
    }
}
