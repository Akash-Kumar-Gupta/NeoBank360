package com.infy.neobank360.loan;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepaymentScheduleService {

    private final LoanRepaymentRepository repository;

    public RepaymentScheduleService(LoanRepaymentRepository repository) {
        this.repository = repository;
    }

    public void generateSchedule(LoanAccount account) {

        double outstanding = account.getPrincipalAmount();
        double monthlyRate = account.getAnnualInterestRate() / 12 / 100;
        int tenure = account.getTenureMonths();
        double emi = account.getEmiAmount();

        // ✅ USE DISBURSED DATE (IMPORTANT FIX)
        LocalDate startDate = account.getDisbursedAt().toLocalDate();

        List<LoanRepayment> repayments = new ArrayList<>();

        for (int i = 1; i <= tenure; i++) {

            double interest = outstanding * monthlyRate;
            double principal = emi - interest;

            // ✅ HANDLE LAST EMI (ADJUST ROUNDING)
            if (i == tenure) {
                principal = outstanding;
                interest = emi - principal;
            }

            outstanding -= principal;

            LoanRepayment repayment = new LoanRepayment();

            repayment.setLoanAccountId(account.getId());
            repayment.setInstallmentNumber(i);
            repayment.setDueDate(startDate.plusMonths(i));

            // ✅ ROUND VALUES (VERY IMPORTANT)
            repayment.setEmiAmount(round(emi));
            repayment.setPrincipalComponent(round(principal));
            repayment.setInterestComponent(round(interest));

            repayments.add(repayment);
        }

        // ✅ SAVE ALL AT ONCE (PERFORMANCE 🔥)
        repository.saveAll(repayments);
    }

    // ✅ COMMON ROUNDING METHOD
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
