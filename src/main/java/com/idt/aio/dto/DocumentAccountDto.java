package com.idt.aio.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record DocumentAccountDto(
        Integer docAccountId,
        Integer docId,
        String accountId
) {
    @QueryProjection
    public DocumentAccountDto {

    }
}
