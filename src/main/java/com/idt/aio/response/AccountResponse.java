package com.idt.aio.response;

import com.idt.aio.entity.constant.Role;
import lombok.Builder;

@Builder
public record AccountResponse(
        String accountId,
        String adminId,
        Role role,
        String name

) {
}
