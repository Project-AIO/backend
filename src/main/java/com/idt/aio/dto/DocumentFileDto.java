package com.idt.aio.dto;

import com.idt.aio.entity.DocumentFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class DocumentFileDto {
    private Integer docFileId;
    private Integer docId;
    private String extension;
    private String path;
    private String fileName;
    private String originalFileName;
    private int totalPage;
    private String revision;

    public static DocumentFileDto from(final DocumentFile documentFile) {
        return DocumentFileDto.builder()
                .docFileId(documentFile.getDocFileId())
                .docId(documentFile.getDocument().getDocId())
                .extension(documentFile.getExtension())
                .path(documentFile.getPath())
                .fileName(documentFile.getFileName())
                .originalFileName(documentFile.getOriginalFileName())
                .totalPage(documentFile.getTotalPage())
                .revision(documentFile.getRevision())
                .build();
    }
    public static List<DocumentFileDto> from(final List<DocumentFile> documentFiles) {
        return documentFiles.stream()
                .map(DocumentFileDto::from)
                .toList();
    }
}
