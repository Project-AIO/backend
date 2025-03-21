package com.idt.aio.controller;

import com.idt.aio.entity.Account;
import com.idt.aio.request.ConfigKnowledgeRequest;
import com.idt.aio.request.DocAccountRequest;
import com.idt.aio.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AccountService accountService;


    /*
        사용자 계정 조회
     */

    @GetMapping("/accounts")  //회원가입
    public ResponseEntity<List<Account>> getAccountsList() {
        return ResponseEntity.ok(accountService.getAccounstList());
    }


}
