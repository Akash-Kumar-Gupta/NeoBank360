package com.infy.neobank360.account;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.stereotype.Service;

import com.infy.neobank360.user.User;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    private final AccountRepository repo;
    private final SecureRandom random = new SecureRandom();

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    // ✅ CREATE NEW ACCOUNT
    @Transactional
    public Account createAccount(AccountType type, User user) {
        Account account = new Account();
        account.setAccountType(type);
        account.setUser(user);
        account.setAccountNumber(generateAccountNumber());

        return repo.save(account);
    }

    // ✅ LIST USER ACCOUNTS
    public List<Account> getMyAccounts(User user) {
        return repo.findByUser(user);
    }

    // ✅ FETCH SINGLE ACCOUNT (OWNERSHIP CHECK)
    public Account getAccount(Long accountId, User user) {
        Account account = repo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access");
        }
        return account;
    }

    // -------------------------
    // 🔒 INTERNAL HELPERS
    // -------------------------

    private String generateAccountNumber() {
        String acc;
        do {
            acc = "NB" + (1000000000L + Math.abs(random.nextLong()) % 9000000000L);
        } while (repo.existsByAccountNumber(acc));
        return acc;
    }
}