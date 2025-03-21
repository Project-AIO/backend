package com.idt.aio.service;

import com.idt.aio.entity.DocumentAccount;
import com.idt.aio.repository.DocumentAccountRepository;
import com.idt.aio.response.DocumentAccountResponse;
import com.idt.aio.response.DocumentPartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentAccountService {
    private final DocumentAccountRepository documentAccountRepository;
    public List<DocumentAccountResponse> fetchDocAccountsByDocId(final Integer docId) {
        final List<DocumentAccount> docAccounts = documentAccountRepository.findDocumentAccountByDocument_DocId(docId);
        return DocumentAccountResponse.from(docAccounts);
    }
}
