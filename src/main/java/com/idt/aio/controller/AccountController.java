package com.idt.aio.controller;

import com.idt.aio.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
