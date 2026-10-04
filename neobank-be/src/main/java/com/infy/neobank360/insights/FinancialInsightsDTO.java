package com.infy.neobank360.insights;

import java.math.BigDecimal;
import java.util.List;

public class FinancialInsightsDTO 
{

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal savings;
    private List<TrendEntryDTO> trendSummary;
    private List<String> categories;
    private List<BigDecimal> categoryAmounts;

    
    
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
	public BigDecimal getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}
	public BigDecimal getSavings() {
		return savings;
	}
	public void setSavings(BigDecimal savings) {
		this.savings = savings;
	}
	public List<TrendEntryDTO> getTrendSummary() {
		return trendSummary;
	}
	public void setTrendSummary(List<TrendEntryDTO> trendSummary) {
		this.trendSummary = trendSummary;
	}
    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<BigDecimal> getCategoryAmounts() {
        return categoryAmounts;
    }

    public void setCategoryAmounts(List<BigDecimal> categoryAmounts) {
        this.categoryAmounts = categoryAmounts;
    }
    
}