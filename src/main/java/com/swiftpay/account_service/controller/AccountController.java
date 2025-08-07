package com.swiftpay.account_service.controller;

import com.swiftpay.account_service.dto.AccountDto;
import com.swiftpay.account_service.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody  @Valid  AccountDto accountDto) {
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return ResponseEntity.ok(createdAccount);
    }


    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDto>> getUserAccounts(@PathVariable String userId) {
        List<AccountDto> userAccounts = accountService.getUserAccounts(userId);
        return ResponseEntity.ok(userAccounts);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> closeAccount(@PathVariable String accountId) {
        accountService.closeAccount(accountId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{accountId}/balance")
    public ResponseEntity<Void> updateBalance(@PathVariable String accountId,
                                              @RequestParam Double amount) {
        accountService.updateBalance(accountId, amount);
        return ResponseEntity.ok().build();
    }
}
