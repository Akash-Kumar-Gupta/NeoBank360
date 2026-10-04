package com.infy.neobank360.loan;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

import com.infy.neobank360.account.Account;
import com.infy.neobank360.account.AccountRepository;
import com.infy.neobank360.transaction.CategoryType;
import com.infy.neobank360.transaction.Transaction;
import com.infy.neobank360.transaction.TransactionRepository;
import com.infy.neobank360.transaction.TransactionType;

@Service
public class LoanRepaymentService {

    private final LoanRepaymentRepository repaymentRepository;
    private final LoanAccountRepository accountRepository;

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepository;

    public LoanRepaymentService(
            LoanRepaymentRepository repaymentRepository,
            LoanAccountRepository accountRepository,
            AccountRepository accountRepo,
            TransactionRepository transactionRepository
    ) {
        this.repaymentRepository = repaymentRepository;
        this.accountRepository = accountRepository;
        this.accountRepo = accountRepo;
        this.transactionRepository = transactionRepository;
    }

    // ✅ 1. GET SCHEDULE + OVERDUE LOGIC
    public List<LoanRepayment> getSchedule(Long loanAccountId, Long userId) {

        LoanAccount loanAccount = accountRepository.findById(loanAccountId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loanAccount.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        List<LoanRepayment> repayments =
                repaymentRepository.findByLoanAccountIdOrderByInstallmentNumber(loanAccountId);

        LocalDate today = LocalDate.now();

        boolean updated = false;

        for (LoanRepayment r : repayments) {

            if (r.getPaymentStatus() == LoanRepayment.Status.PENDING
                    && r.getDueDate().isBefore(today)) {

                r.setPaymentStatus(LoanRepayment.Status.OVERDUE);
                updated = true;
            }
        }

        if (updated) {
            repaymentRepository.saveAll(repayments);
        }

        return repayments;
    }


    // ✅ 2. PAY EMI (CORRECT FINAL VERSION )
    public void payEmi(Long repaymentId, Long accountId, Long userId) {

        // ✅ FETCH REPAYMENT
        LoanRepayment repayment = repaymentRepository.findById(repaymentId)
                .orElseThrow(() -> new RuntimeException("Repayment not found"));

        if (repayment.getPaymentStatus() == LoanRepayment.Status.PAID) {
            throw new RuntimeException("EMI already paid");
        }

        // ✅ FETCH LOAN
        LoanAccount loanAccount = accountRepository.findById(repayment.getLoanAccountId())
                .orElseThrow(() -> new RuntimeException("Loan account not found"));

        if (!loanAccount.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        // ✅ FETCH ACCOUNT
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal emiAmount = BigDecimal.valueOf(repayment.getEmiAmount());

        // ✅ CHECK BALANCE
        if (account.getBalance().compareTo(emiAmount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // ✅ DEDUCT BALANCE
        BigDecimal updatedBalance = account.getBalance().subtract(emiAmount);
        account.setBalance(updatedBalance);
        accountRepo.save(account);

        // ✅ UPDATE REPAYMENT
        repayment.setPaymentStatus(LoanRepayment.Status.PAID);
        repayment.setPaidAt(LocalDateTime.now());
        repaymentRepository.save(repayment);

        Transaction txn = new Transaction();

        txn.setAccount(account);
        txn.setAmount(emiAmount);
        txn.setType(TransactionType.DEBIT);
        txn.setBalanceAfter(updatedBalance);
        txn.setCategory(CategoryType.LOAN_REPAYMENT);

        transactionRepository.save(txn);
    }
}
