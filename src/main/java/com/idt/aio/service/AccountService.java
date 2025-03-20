package com.idt.aio.service;

import com.idt.aio.entity.Account;
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
}
