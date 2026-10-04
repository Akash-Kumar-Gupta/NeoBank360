package com.infy.neobank360.transaction;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.infy.neobank360.user.User;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    // ✅ CREDIT
    @PostMapping("/credit")
    public Transaction credit(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount,
            @RequestParam CategoryType category,  // ✅ NEW
            @AuthenticationPrincipal User user) {

        return service.credit(accountId, amount, category, user);
    }

    // ✅ DEBIT
    @PostMapping("/debit")
    public Transaction debit(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount,
            @RequestParam CategoryType category,  // ✅ NEW
            @AuthenticationPrincipal User user) {

        return service.debit(accountId, amount, category, user);
    }

    // ✅ TRANSFER
    @PostMapping("/transfer")
    public void transfer(
            @RequestParam Long fromAccountId,
            @RequestParam String toAccountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam CategoryType category,  // ✅ NEW
            @AuthenticationPrincipal User user
    ) {
        service.transferByAccountNumber(
                fromAccountId,
                toAccountNumber,
                amount,
                category,
                user
        );
    }

    // ✅ HISTORY
    @GetMapping("/{accountId}")
    public Page<Transaction> history(
            @PathVariable Long accountId,
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return service.history(accountId, user, pageable);
    }
}