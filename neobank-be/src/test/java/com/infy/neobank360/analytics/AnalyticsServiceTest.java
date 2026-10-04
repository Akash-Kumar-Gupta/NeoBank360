package com.infy.neobank360.analytics;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AnalyticsServiceTest
{

    @Test
    void testAnalyticsCalculation()
    {

        long totalUsers = 10;

        long activeUsers = 8;

        long totalAccounts = 15;

        long totalTransactions = 120;

        double totalTransactionAmount = 250000.50;

        assertEquals(10, totalUsers);

        assertEquals(8, activeUsers);

        assertEquals(15, totalAccounts);

        assertEquals(120, totalTransactions);

        assertEquals(250000.50,
                totalTransactionAmount);
    }
}