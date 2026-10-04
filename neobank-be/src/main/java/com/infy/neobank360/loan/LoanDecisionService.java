//package com.infy.neobank360.loan;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class LoanDecisionService 
//{
//
//    private final LoanApplicationRepository repository;
//
//    public LoanDecisionService(LoanApplicationRepository repository) 
//    {
//        this.repository = repository;
//    }
//
//    public LoanApplication decide(Long id, LoanDecisionDTO dto) 
//    {
//
//        // ✅ Fetch application
//        LoanApplication app = repository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
//
//        // ✅ Apply decision
//        if ("APPROVE".equalsIgnoreCase(dto.getDecision())) 
//        {
//            app.setStatus(LoanApplication.Status.APPROVED);
//            
//        }
//        
//        else if ("REJECT".equalsIgnoreCase(dto.getDecision())) 
//        {
//            app.setStatus(LoanApplication.Status.REJECTED);
//        }
//        
//        else 
//        {
//            throw new RuntimeException("Invalid decision");
//        }
//
//        return repository.save(app);
//    }
//}


package com.infy.neobank360.loan;

import org.springframework.stereotype.Service;

@Service
public class LoanDecisionService 
{

    private final LoanApplicationRepository repository;
    private final LoanProductRepository loanProductRepository;
    private final LoanAccountService loanAccountService;

    // ✅ UPDATED CONSTRUCTOR
    public LoanDecisionService(LoanApplicationRepository repository,
                               LoanProductRepository loanProductRepository,
                               LoanAccountService loanAccountService) 
    {
        this.repository = repository;
        this.loanProductRepository = loanProductRepository;
        this.loanAccountService = loanAccountService;
    }

    public LoanApplication decide(Long id, LoanDecisionDTO dto) 
    {

        // ✅ Fetch application
        LoanApplication app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // ✅ Apply decision
        if ("APPROVE".equalsIgnoreCase(dto.getDecision())) 
        {
            app.setStatus(LoanApplication.Status.APPROVED);

            // ✅ FETCH LOAN PRODUCT
            LoanProduct product = loanProductRepository
                    .findById(app.getLoanProductId())
                    .orElseThrow(() -> new RuntimeException("Loan product not found"));

            double interestRate = product.getAnnualInterestRate();

            // ✅ CREATE LOAN ACCOUNT (MAIN FIX 🔥)
            loanAccountService.createLoanAccount(app, interestRate);
        }
        
        else if ("REJECT".equalsIgnoreCase(dto.getDecision())) 
        {
            app.setStatus(LoanApplication.Status.REJECTED);
        }
        
        else 
        {
            throw new RuntimeException("Invalid decision");
        }

        // ✅ Save application
        return repository.save(app);
    }
}