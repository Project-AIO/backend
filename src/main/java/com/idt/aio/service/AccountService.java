package com.idt.aio.service;

import com.idt.aio.dto.AccountDto;
import com.idt.aio.entity.Account;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.AccountRepository;
import java.util.List;
import java.util.UUID;

import com.idt.aio.request.AccountRequest;
import com.idt.aio.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final EncryptUtil encryptUtil;

    public AccountService(AccountRepository accountRepository, EncryptUtil encryptUtil) {
        this.accountRepository = accountRepository;
        this.encryptUtil = encryptUtil;
    }

    @Transactional(readOnly = true)
    public List<AccountDto> getAccounstList(String page_num) throws Exception {
        List<AccountDto> accountDto = AccountDto.from(accountRepository.findAll());

        for (int i=0; i < accountDto.size(); i++) {
            accountDto.get(i).setExpiredDt(encryptUtil.selLicenseKeyToDate(accountDto.get(i).getLicenseKey()));
            accountDto.get(i).setLicenseKey(null);
        }

        return accountDto;
    }

    @Transactional(readOnly = true)
    public List<Account> getAccountsByIds(final List<String> accountIds) {
        final List<Account> accounts = accountRepository.findAllById(accountIds);
        if(accounts.size() != accountIds.size()) {
            throw DomainExceptionCode.ACCOUNT_NOT_FOUND.newInstance();
        }
        return accounts;
    }

    @Transactional
    public ResponseEntity<?> createUser(AccountRequest request) {
        String accountId = UUID.randomUUID().toString();
        String adminId = request.adminId();
        String name = request.name();
        String role = request.role();

        accountRepository.saveAccount(accountId, adminId, name, role);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public void delUser(String accountId) {
        accountRepository.deleteByAccountId(accountId);
    }

}
