package com.idt.aio.response;

import com.idt.aio.entity.constant.Extension;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record DocumentFileData(
        Integer docFileId,
        String extension,
        String path,
        String fileName,
        String originalFileName,
        int totalPage,
        String revision
) {
    @QueryProjection
    public DocumentFileData {
    }
}
