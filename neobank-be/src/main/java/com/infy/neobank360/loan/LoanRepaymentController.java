package com.infy.neobank360.loan;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.infy.neobank360.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/loan-repayments")
public class LoanRepaymentController {

    private final LoanRepaymentService service;

    public LoanRepaymentController(LoanRepaymentService service) {
        this.service = service;
    }

    // =====================================
    // ✅ 1. GET EMI SCHEDULE
    // =====================================
    @GetMapping("/{loanAccountId}")
    public List<LoanRepayment> getSchedule(@PathVariable Long loanAccountId,
                                           @AuthenticationPrincipal User user) {

        if (user == null) {
            throw new RuntimeException("User not authenticated");
        }

        return service.getSchedule(loanAccountId, user.getId());
    }

    // =====================================
    // ✅ 2. PAY EMI
    // =====================================
    @PostMapping("/pay")
    public String payEmi(@RequestBody PaymentRequestDTO dto,
                         @AuthenticationPrincipal User user) {

        if (user == null) {
            throw new RuntimeException("User not authenticated");
        }

        if (dto.getRepaymentId() == null || dto.getAccountId() == null) {
            throw new RuntimeException("Invalid request");
        }

        service.payEmi(
                dto.getRepaymentId(),
                dto.getAccountId(),
                user.getId()
        );

        return "EMI Paid Successfully ✅";
    }
}