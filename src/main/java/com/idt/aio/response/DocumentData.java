package com.idt.aio.response;

import com.idt.aio.entity.constant.State;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DocumentData(
        Integer docId,
        Integer projectFolderId,
        String name,
        State state,
        DocumentFileData fileData,
        LocalDateTime uploadDt
) {
    @QueryProjection
    public DocumentData {
    }

}
