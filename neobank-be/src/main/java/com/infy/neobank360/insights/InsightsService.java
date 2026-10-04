package com.infy.neobank360.insights;

import org.springframework.stereotype.Service;

import com.infy.neobank360.transaction.CategoryType;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsightsService 
{

    private final InsightsRepository repo;

    public InsightsService(InsightsRepository repo)
    {
        this.repo = repo;
    }

    public FinancialInsightsDTO getInsights(Long userId) 
    {

        BigDecimal income = repo.getTotalIncome(userId);
        BigDecimal expense = repo.getTotalExpense(userId);
        BigDecimal savings = income.subtract(expense);

        // ✅ Formatter for "May 2026"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        List<TrendEntryDTO> trend = repo.getMonthlyTrend(userId)
                .stream()
                .map(obj -> {

                    // ✅ DB gives "2026-05"
                    String rawMonth = (String) obj[0];

                    // ✅ Convert to "May 2026"
                    String formattedMonth = YearMonth.parse(rawMonth)
                            .format(formatter);

                    return new TrendEntryDTO(
                            formattedMonth,
                            (BigDecimal) obj[1],
                            (BigDecimal) obj[2]
                    );
                })
                .collect(Collectors.toList());
        

         List<Object[]> categoryData = repo.getExpenseByCategory(userId);

         List<String> categories = categoryData.stream().map(obj -> ((CategoryType) obj[0]).name()).toList();

         List<BigDecimal> amounts = categoryData.stream().map(obj -> (BigDecimal) obj[1]).toList();




        FinancialInsightsDTO dto = new FinancialInsightsDTO();
        dto.setTotalIncome(income);
        dto.setTotalExpense(expense);
        dto.setSavings(savings);
        dto.setTrendSummary(trend);
        dto.setCategories(categories);
        dto.setCategoryAmounts(amounts);

        return dto;
    }
}
