package com.infy.neobank360.budget;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.neobank360.transaction.CategoryType;

public interface BudgetRepository extends JpaRepository<Budget, Long> 
{

    Optional<Budget> findByUserIdAndCategoryAndBudgetMonth(
        Long userId,
        CategoryType category,
        LocalDate month
    );
    
    List<Budget> findByUserIdAndBudgetMonth(Long userId, LocalDate month);
}
