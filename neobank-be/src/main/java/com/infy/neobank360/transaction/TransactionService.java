package com.infy.neobank360.transaction;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infy.neobank360.account.Account;
import com.infy.neobank360.account.AccountRepository;
import com.infy.neobank360.user.User;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository txnRepo;
    private final AccountRepository accountRepo;

    public TransactionService(TransactionRepository txnRepo, AccountRepository accountRepo) {
        this.txnRepo = txnRepo;
        this.accountRepo = accountRepo;
    }

    @Transactional
    public Transaction credit(Long accountId, BigDecimal amount, CategoryType category, User user) {
        Account account = loadOwnedAccount(accountId, user);
        validateAmount(amount);
        validateCategory(category);

        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepo.save(account);

        return saveTransaction(account, amount, TransactionType.CREDIT, category, newBalance);
    }

    @Transactional
    public Transaction debit(Long accountId, BigDecimal amount, CategoryType category, User user) {
        Account account = loadOwnedAccount(accountId, user);
        validateAmount(amount);
        validateCategory(category);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountRepo.save(account);

        return saveTransaction(account, amount, TransactionType.DEBIT, category, newBalance);
    }

    private Transaction createTxn(Account account, BigDecimal amount, TransactionType type, CategoryType category) {
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setType(type);
        tx.setCategory(category);
        tx.setBalanceAfter(account.getBalance());
        return tx;
    }

    public void transferByAccountNumber(Long fromAccountId, String toAccountNumber,
                                        BigDecimal amount, CategoryType category, User user) {

        validateCategory(category);

        Account from = accountRepo.findById(fromAccountId).orElseThrow();

        if (!from.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        Account to = accountRepo.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        txnRepo.save(createTxn(from, amount, TransactionType.DEBIT, category));
        txnRepo.save(createTxn(to, amount, TransactionType.CREDIT, category));
    }

    public Page<Transaction> history(Long accountId, User user, Pageable pageable) {

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return txnRepo.findByAccount(account, pageable);
    }

    // ✅ HELPERS

    private Account loadOwnedAccount(Long accountId, User user) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access");
        }
        return account;
    }

    private void validateAmount(BigDecimal amt) {
        if (amt == null || amt.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid transaction amount");
        }
    }

    private void validateCategory(CategoryType category) {
        if (category == null) {
            throw new IllegalArgumentException("Category is required");
        }
    }

    private Transaction saveTransaction(Account account, BigDecimal amount,
                                        TransactionType type, CategoryType category,
                                        BigDecimal balanceAfter) {

        Transaction txn = new Transaction();
        txn.setAccount(account);
        txn.setAmount(amount);
        txn.setType(type);
        txn.setCategory(category);
        txn.setBalanceAfter(balanceAfter);

        return txnRepo.save(txn);
    }
}