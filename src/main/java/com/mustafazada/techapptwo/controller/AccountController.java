package com.mustafazada.techapptwo.controller;

import com.mustafazada.techapptwo.dto.request.AccountToAccountRequestDTO;
import com.mustafazada.techapptwo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account")
    public ResponseEntity<?> account() {
        return new ResponseEntity<>(accountService.getAccount(), HttpStatus.OK);
    }


    @PostMapping("/transfer")
    public ResponseEntity<?> amountTransfer(@RequestBody AccountToAccountRequestDTO accountToAccountRequestDTO) {
        return new ResponseEntity<>(accountService.account2account(accountToAccountRequestDTO), HttpStatus.OK);
    }
}
