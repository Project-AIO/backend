package com.idt.aio.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record ProjectAccountDto(
        Integer projectAccountId,
        Integer projectId,
        String accountId
) {
    @QueryProjection
    public ProjectAccountDto {

    }
}
