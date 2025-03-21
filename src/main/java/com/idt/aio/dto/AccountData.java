package com.idt.aio.dto;

import com.idt.aio.entity.constant.Role;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record AccountData(
        String accountId,
        String adminId,
        Role role,
        String name
) {
    @QueryProjection
    public AccountData {

    }
}
