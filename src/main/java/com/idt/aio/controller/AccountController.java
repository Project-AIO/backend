package com.idt.aio.controller;

import com.idt.aio.dto.AccountDto;
import com.idt.aio.dto.UserDto;
import com.idt.aio.entity.Account;
import com.idt.aio.repository.AccountRepository;
import com.idt.aio.service.AccountService;
import com.idt.aio.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private final AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /*
        사용자 계정 조회
     */
    @GetMapping("/v1/accounts")  //회원가입
    public ResponseEntity<List<Account>> getAccounstList() {
        return ResponseEntity.ok(accountService.getAccounstList());
    }
}
