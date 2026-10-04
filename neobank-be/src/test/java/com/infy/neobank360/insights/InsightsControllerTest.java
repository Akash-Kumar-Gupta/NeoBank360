package com.infy.neobank360.insights;


import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class InsightsControllerTest
{

    @Test
    void testInsightsValues()
    {

        FinancialInsightsDTO dto =
                new FinancialInsightsDTO();

        dto.setTotalIncome(
                BigDecimal.valueOf(100000)
        );

        dto.setTotalExpense(
                BigDecimal.valueOf(40000)
        );

        dto.setSavings(
                BigDecimal.valueOf(60000)
        );

        dto.setTrendSummary(
                new ArrayList<>()
        );

        assertNotNull(dto);

        assertEquals(
                BigDecimal.valueOf(100000),
                dto.getTotalIncome()
        );

        assertEquals(
                BigDecimal.valueOf(60000),
                dto.getSavings()
        );
    }
}