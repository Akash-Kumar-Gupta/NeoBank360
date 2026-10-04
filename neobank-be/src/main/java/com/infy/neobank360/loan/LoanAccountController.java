package com.infy.neobank360.loan;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-accounts")
public class LoanAccountController
{
    private final LoanAccountRepository repository;

    public LoanAccountController(LoanAccountRepository repository) 
    {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public LoanAccount getLoan(@PathVariable Long id) 
    {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }
    

    @GetMapping("/by-application/{appId}")
    public LoanAccount getByApplicationId(@PathVariable Long appId) 
    {

        return repository.findByLoanApplicationId(appId)
                .orElseThrow(() -> new RuntimeException("Loan account not found"));
    }

}
