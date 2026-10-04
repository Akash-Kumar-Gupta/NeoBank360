package com.infy.neobank360.insights;


import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class InsightsServiceTest
{

    @Test
    void testSavingsCalculation()
    {

        BigDecimal income =
                BigDecimal.valueOf(50000);

        BigDecimal expense =
                BigDecimal.valueOf(20000);

        BigDecimal savings =
                income.subtract(expense);

        assertEquals(
                BigDecimal.valueOf(30000),
                savings
        );
    }

    @Test
    void testTrendEntryDTO()
    {

        TrendEntryDTO dto =
                new TrendEntryDTO(
                        "May 2026",
                        BigDecimal.valueOf(25000),
                        BigDecimal.valueOf(10000)
                );

        assertEquals(
                "May 2026",
                dto.getMonth()
        );

        assertEquals(
                BigDecimal.valueOf(25000),
                dto.getIncome()
        );

        assertEquals(
                BigDecimal.valueOf(10000),
                dto.getExpense()
        );
    }

    @Test
    void testFinancialInsightsDTO()
    {

        FinancialInsightsDTO dto =
                new FinancialInsightsDTO();

        dto.setTotalIncome(
                BigDecimal.valueOf(70000)
        );

        dto.setTotalExpense(
                BigDecimal.valueOf(25000)
        );

        dto.setSavings(
                BigDecimal.valueOf(45000)
        );

        dto.setTrendSummary(
                new ArrayList<>()
        );

        assertEquals(
                BigDecimal.valueOf(70000),
                dto.getTotalIncome()
        );

        assertEquals(
                BigDecimal.valueOf(25000),
                dto.getTotalExpense()
        );

        assertEquals(
                BigDecimal.valueOf(45000),
                dto.getSavings()
        );
    }
}