package com.infy.neobank360.account;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.neobank360.user.User;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // ✅ CREATE ACCOUNT
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Account create(@RequestParam AccountType type,
                          @AuthenticationPrincipal User user) {
        return service.createAccount(type, user);
    }

    // ✅ LIST MY ACCOUNTS
    @GetMapping
    public List<Account> list(@AuthenticationPrincipal User user) {
        return service.getMyAccounts(user);
    }

    // ✅ GET ACCOUNT DETAILS
    @GetMapping("/{accountId}")
    public Account get(@PathVariable Long accountId,
                       @AuthenticationPrincipal User user) {
        return service.getAccount(accountId, user);
    }
}