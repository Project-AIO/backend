package com.idt.aio.response;

import com.idt.aio.entity.DocumentAccount;
import lombok.Builder;

import java.util.List;

@Builder
public record  DocumentAccountResponse(
        Integer docAccountId,
        Integer docId,
        String accountId
) {
    public static List<DocumentAccountResponse> from(final List<DocumentAccount> documentAccounts){
        return documentAccounts.stream()
                .map(documentAccount -> DocumentAccountResponse.builder()
                        .docAccountId(documentAccount.getDocAccountId())
                        .docId(documentAccount.getDocument().getDocId())
                        .accountId(documentAccount.getAccount().getAccountId())
                        .build())
                .toList();
    }
}
