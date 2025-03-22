package com.idt.aio.service;

import com.idt.aio.entity.Account;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.DocumentAccount;
import com.idt.aio.repository.DocumentAccountRepository;
import com.idt.aio.response.DocumentAccountResponse;
import com.idt.aio.response.DocumentPartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DocumentAccountService {
    private final DocumentAccountRepository documentAccountRepository;
    private final AccountService accountService;
    private final DocumentService documentService;

    @Transactional(readOnly = true)
    public List<DocumentAccountResponse> fetchDocAccountsByDocId(final Integer docId) {
        final List<DocumentAccount> docAccounts = documentAccountRepository.findDocumentAccountByDocument_DocId(docId);
        return DocumentAccountResponse.from(docAccounts);
    }

    @Transactional
    public void bulkSaveDocAccount(final Integer docId, final List<String> accountIds) {
        final List<Account> accounts = accountService.getAccountsByIds(accountIds);
        final Document document = documentService.getDocumentById(docId);

        final List<DocumentAccount> documentAccounts = accounts.stream()
                .map(account ->
                        DocumentAccount.builder()
                                .document(document)
                                .account(account)
                                .build()
                ).toList();

        documentAccountRepository.saveAll(documentAccounts);
    }

    public List<DocumentAccountResponse> getPermittedAccountsByDocId(final Integer docId) {
        final List<DocumentAccount> docAccounts = documentAccountRepository.findAllByDocAccountId(docId);
        return DocumentAccountResponse.from(docAccounts);
    }
}
