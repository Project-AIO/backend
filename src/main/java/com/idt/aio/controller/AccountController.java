package com.idt.aio.controller;

import com.idt.aio.request.AccountRequest;
import com.idt.aio.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "사용자 계정 목록 조회 API", description = """
               사용자 계정 목록을 조회
            """)
    @GetMapping("/v1/accounts")
    public ResponseEntity<?> getAccounstList(@RequestParam("page") String page_num) throws Exception {
        return ResponseEntity.ok(accountService.getAccounstList(page_num));
    }

    @Operation(summary = "사용자 계정 등록 API", description = """
               사용자 계정을 등록
            """)
    @PostMapping("/v1/account")  //회원가입
    public ResponseEntity<?> createUser(@RequestBody @Valid final AccountRequest request) {
        accountService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "사용자 계정 삭제 API", description = """
               사용자 계정을 삭제
            """)
    @DeleteMapping("/v1/account/{account_id}")  //회원가입
    public void delUser(@PathVariable("account_id") String account_id) {
        accountService.delUser(account_id);
    }

}
