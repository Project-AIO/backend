package com.idt.aio.dto;


import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.Builder;

@Builder
public record AccountPermissionDto(
        DocumentAccountDto documentAccountDto,
        ProjectAccountDto projectAccountDto,
        AccountData accountDto
) {
    @QueryProjection
    public AccountPermissionDto {

    }
}
