package com.idt.aio.service;

import com.idt.aio.dto.AccountDto;
import com.idt.aio.dto.UserDto;
import com.idt.aio.entity.Account;
import com.idt.aio.exception.NotFoundMemberException;
import com.idt.aio.repository.AccountRepository;
import com.idt.aio.repository.UserRepository;
import com.idt.aio.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional (readOnly = true)
    public List<Account> getAccounstList() {
        return accountRepository.findAll();
    }
}
