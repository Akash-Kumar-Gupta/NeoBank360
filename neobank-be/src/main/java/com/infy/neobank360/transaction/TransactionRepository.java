package com.infy.neobank360.transaction;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.neobank360.account.Account;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountOrderByCreatedAtDesc(Account account);

    Page<Transaction> findByAccountAndType(Account account, TransactionType type, Pageable pageable);

    Page<Transaction> findByAccount(Account account, Pageable pageable);

    List<Transaction> findByAccountAndCategoryAndCreatedAtBetween(
            Account account,
            CategoryType category,
            Instant start,
            Instant end
    );
    
}