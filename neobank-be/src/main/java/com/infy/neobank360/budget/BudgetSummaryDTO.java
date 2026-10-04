package com.infy.neobank360.budget;

import com.infy.neobank360.transaction.CategoryType;

public class BudgetSummaryDTO {

    private CategoryType category;
    private Double limit;
    private Double spent;
    private Double remaining;
    private Double percentUsed;

    public BudgetSummaryDTO(CategoryType category, Double limit, Double spent) 
    {
        this.category = category;
        this.limit = limit;
        this.spent = spent;

        this.remaining = limit - spent;

        this.percentUsed = (limit > 0)
                ? (spent / limit) * 100
                : 0;
    }


    public CategoryType getCategory() { return category; }
    public Double getLimit() { return limit; }
    public Double getSpent() { return spent; }
    public Double getRemaining() { return remaining; }
    public Double getPercentUsed() { return percentUsed; }
}