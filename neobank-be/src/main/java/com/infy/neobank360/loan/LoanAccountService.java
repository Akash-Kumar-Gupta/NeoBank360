package com.infy.neobank360.loan;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class LoanAccountService {

    private final LoanAccountRepository accountRepository;
    private final RepaymentScheduleService repaymentScheduleService;

    public LoanAccountService(LoanAccountRepository accountRepository,
                              RepaymentScheduleService repaymentScheduleService) {
        this.accountRepository = accountRepository;
        this.repaymentScheduleService = repaymentScheduleService;
    }

    // ✅ ✅ NEW METHOD: Generate Loan Account Number
    private String generateLoanAccountNumber() {
        return "LN" + System.currentTimeMillis();
    }

    public LoanAccount createLoanAccount(LoanApplication app, double interestRate) {

        double principal = app.getRequestedAmount();
        int tenure = app.getRequestedTenureMonths();

        double emi = EmiCalculatorUtil.calculateEMI(
                principal,
                interestRate,
                tenure
        );

        LoanAccount account = new LoanAccount();

        // ✅ ✅ ADD THIS LINE (VERY IMPORTANT)
        account.setLoanAccountNumber(generateLoanAccountNumber());

        account.setLoanApplicationId(app.getId());
        account.setUserId(app.getUserId());
        account.setPrincipalAmount(principal);
        account.setAnnualInterestRate(interestRate);
        account.setTenureMonths(tenure);
        account.setEmiAmount(emi);
        account.setDisbursedAt(LocalDateTime.now());

        LoanAccount saved = accountRepository.save(account);

        // ✅ Generate EMI schedule
        repaymentScheduleService.generateSchedule(saved);

        return saved;
    }
}
