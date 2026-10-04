package com.infy.neobank360.insights;

import com.infy.neobank360.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface InsightsRepository extends JpaRepository<Transaction, Long>
{

    // ✅ TOTAL INCOME
    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.account.user.id = :userId
        AND t.type = 'CREDIT'
    """)
    BigDecimal getTotalIncome(Long userId);

    // ✅ TOTAL EXPENSE
    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.account.user.id = :userId
        AND t.type = 'DEBIT'
    """)
    BigDecimal getTotalExpense(Long userId);

    // ✅ MONTHLY TREND
    @Query("""
    	    SELECT 
    	    FUNCTION('DATE_FORMAT', t.createdAt, '%Y-%m'),
    	    SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE 0 END),
    	    SUM(CASE WHEN t.type = 'DEBIT' THEN t.amount ELSE 0 END)
    	    FROM Transaction t
    	    WHERE t.account.user.id = :userId
    	    GROUP BY FUNCTION('DATE_FORMAT', t.createdAt, '%Y-%m')
    	    ORDER BY FUNCTION('DATE_FORMAT', t.createdAt, '%Y-%m')
    	""")
    	List<Object[]> getMonthlyTrend(Long userId);
    	
    @Query("""
    	    SELECT t.category, SUM(t.amount)
    	    FROM Transaction t
    	    WHERE t.account.user.id = :userId
    	      AND t.type = 'DEBIT'
    	    GROUP BY t.category
    	""")
    	List<Object[]> getExpenseByCategory(Long userId);
    
}
