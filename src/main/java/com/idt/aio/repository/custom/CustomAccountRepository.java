package com.idt.aio.repository.custom;

import com.idt.aio.dto.AccountPermissionDto;
import org.springframework.data.domain.Page;

public interface CustomAccountRepository {
    Page<AccountPermissionDto> getAccountPermissionPageByProjectId(final Integer projectId, final int page, final int size);
}
