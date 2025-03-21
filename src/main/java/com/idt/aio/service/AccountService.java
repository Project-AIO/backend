package com.idt.aio.service;

import com.idt.aio.entity.Account;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public List<Account> getAccounstList() {
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Account> getAccountsByIds(final List<String> accountIds) {
        final List<Account> accounts = accountRepository.findAllById(accountIds);
        if(accounts.size() != accountIds.size()) {
            throw DomainExceptionCode.ACCOUNT_NOT_FOUND.newInstance();
        }
        return accounts;

    }


}
