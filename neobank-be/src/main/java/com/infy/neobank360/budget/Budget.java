package com.infy.neobank360.budget;

import java.time.LocalDate;

import com.infy.neobank360.transaction.CategoryType;

import jakarta.persistence.*;

@Entity
@Table(name = "budgets",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "category", "budget_month"})
    })
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType category;

    @Column(name = "budget_month", nullable = false)
    private LocalDate budgetMonth;

    @Column(name = "limit_amount", nullable = false)
    private Double limitAmount;

    // ✅ getters & setters

    public Long getId() { return id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public CategoryType getCategory() { return category; }

    public void setCategory(CategoryType category) { this.category = category; }

    public LocalDate getBudgetMonth() { return budgetMonth; }

    public void setBudgetMonth(LocalDate budgetMonth) { this.budgetMonth = budgetMonth; }

    public Double getLimitAmount() { return limitAmount; }

    public void setLimitAmount(Double limitAmount) { this.limitAmount = limitAmount; }
}