package com.infy.neobank360.analytics;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infy.neobank360.user.User;
import com.infy.neobank360.transaction.Transaction;

@Repository
public interface AnalyticsRepository extends JpaRepository<User, Long> {

    // ✅ EXISTING METHODS

    @Query("SELECT COUNT(u) FROM User u")
    long getTotalUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    long getActiveUsers();

    @Query("SELECT COUNT(a) FROM Account a")
    long getTotalAccounts();

    @Query("SELECT COUNT(t) FROM Transaction t")
    long getTotalTransactions();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t")
    double getTotalTransactionAmount();

    @Query("SELECT t FROM Transaction t ORDER BY t.createdAt DESC")
    List<Transaction> getAllTransactions();


    // ✅ Transactions after timeframe
    @Query("SELECT t FROM Transaction t WHERE t.createdAt >= :startDate ORDER BY t.createdAt ASC")
    List<Transaction> findTransactionsAfter(java.time.LocalDateTime startDate);

    // ✅ Daily aggregation (for charts)
    @Query("""
        SELECT t.createdAt, 
               SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE 0 END),
               SUM(CASE WHEN t.type = 'DEBIT' THEN t.amount ELSE 0 END)
        FROM Transaction t
        WHERE t.createdAt >= :startDate
        GROUP BY t.createdAt
        ORDER BY t.createdAt
    """)
    List<Object[]> getDailyTransactionStats(@Param("startDate") Instant startDate);

    // ✅ Loan distribution
    @Query("SELECT l.status, COUNT(l) FROM LoanApplication l GROUP BY l.status")
    List<Object[]> getLoanDistribution();

    // ✅ NPA count
    @Query("SELECT COUNT(l) FROM LoanApplication l WHERE l.status = 'NPA'")
    long getNpaCount();

    // ✅ Total loans
    @Query("SELECT COUNT(l) FROM LoanApplication l")
    long getTotalLoans();
}