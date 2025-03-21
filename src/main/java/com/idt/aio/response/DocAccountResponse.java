package com.idt.aio.response;

import com.idt.aio.dto.DocumentAccountDto;
import com.idt.aio.entity.DocumentAccount;
import java.util.List;
import lombok.Builder;

@Builder
public record DocAccountResponse(
        Integer docAccountId,
        Integer docId,
        String accountId
) {
    public static List<DocAccountResponse> from(final List<DocumentAccount> accounts) {
        return accounts.stream()
                .map(account -> DocAccountResponse.builder()
                        .docAccountId(account.getDocAccountId())
                        .docId(account.getDocument().getDocId())
                        .accountId(account.getAccount().getAccountId())
                        .build())
                .toList();
    }
}
