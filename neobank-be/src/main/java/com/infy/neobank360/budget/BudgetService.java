package com.infy.neobank360.budget;

import java.time.*;
import java.util.*;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.infy.neobank360.account.Account;
import com.infy.neobank360.account.AccountRepository;
import com.infy.neobank360.transaction.Transaction;
import com.infy.neobank360.transaction.TransactionRepository;
import com.infy.neobank360.transaction.TransactionType;

@Service
public class BudgetService {

    private final BudgetRepository repo;
    private final TransactionRepository txnRepo;
    private final AccountRepository accountRepo;

    public BudgetService(BudgetRepository repo,
                         TransactionRepository txnRepo,
                         AccountRepository accountRepo) {
        this.repo = repo;
        this.txnRepo = txnRepo;
        this.accountRepo = accountRepo;
    }

    // ✅ CREATE BUDGET
    public Budget create(Long userId, Budget budget) {

        if (budget.getLimitAmount() <= 0) {
            throw new RuntimeException("Invalid budget amount");
        }

        var existing = repo.findByUserIdAndCategoryAndBudgetMonth(
            userId,
            budget.getCategory(),
            budget.getBudgetMonth()
        );

        if (existing.isPresent()) {
            throw new RuntimeException("Budget already exists");
        }

        budget.setUserId(userId);

        return repo.save(budget);
    }

    // 🚀 ✅ GET SUMMARY (FINAL FIXED VERSION)
    public List<BudgetSummaryDTO> getSummary(Long userId, YearMonth yearMonth) {

        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();

        Instant start = firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = lastDay.atTime(LocalTime.MAX)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        // ✅ Fetch budgets
        List<Budget> budgets = repo.findByUserIdAndBudgetMonth(userId, firstDay);

        // ✅ Get user accounts
        List<Account> accounts = accountRepo.findByUserId(userId);

        List<BudgetSummaryDTO> result = new ArrayList<>();

        for (Budget budget : budgets) {

            double spent = 0;

            for (Account acc : accounts) {

                List<Transaction> txns =
                        txnRepo.findByAccountAndCategoryAndCreatedAtBetween(
                                acc,
                                budget.getCategory(),
                                start,
                                end
                        );

                for (Transaction txn : txns) {

                    // ✅ count only expenses (IMPORTANT FIX clarity)
                    if (txn.getType() == TransactionType.DEBIT) {

                        BigDecimal amt = txn.getAmount().abs();
                        spent += amt.doubleValue();
                    }
                }
            }

            result.add(new BudgetSummaryDTO(
                    budget.getCategory(),
                    budget.getLimitAmount(),
                    spent
            ));
        }

        return result;
    }
}
