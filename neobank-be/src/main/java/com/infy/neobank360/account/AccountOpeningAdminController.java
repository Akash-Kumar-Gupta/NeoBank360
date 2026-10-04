package com.infy.neobank360.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/account-opening/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AccountOpeningAdminController {

    private final AccountOpeningRequestRepository requestRepo;
    private final AccountRepository accountRepo;

    public AccountOpeningAdminController(
            AccountOpeningRequestRepository requestRepo,
            AccountRepository accountRepo
    ) {
        this.requestRepo = requestRepo;
        this.accountRepo = accountRepo;
    }

    // ✅ View all pending requests
    @GetMapping("/requests")
    public List<AccountOpeningRequest> getPendingRequests() {
        return requestRepo.findByStatus(RequestStatus.PENDING);
    }

    // ✅ Approve request → create account
    @PostMapping("/approve/{id}")
    @Transactional
    public void approve(@PathVariable Long id) {

        AccountOpeningRequest req = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Mark request approved
        req.setStatus(RequestStatus.APPROVED);

        // ✅ Create real account
        Account account = new Account();
        account.setUser(req.getUser());
        account.setAccountType(req.getAccountType());
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);

        accountRepo.save(account);
    }

    // ✅ Reject request
    @PostMapping("/reject/{id}")
    public void reject(@PathVariable Long id) {
        AccountOpeningRequest req = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        req.setStatus(RequestStatus.REJECTED);
    }

    private String generateAccountNumber() {
        return "NB" + System.currentTimeMillis();
    }
}
