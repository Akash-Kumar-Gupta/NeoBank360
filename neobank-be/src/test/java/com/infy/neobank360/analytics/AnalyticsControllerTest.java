package com.infy.neobank360.analytics;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AnalyticsControllerTest
{

    @Test
    void testAdminAnalyticsDTO()
    {

        AdminAnalyticsDTO dto =
                new AdminAnalyticsDTO();

        dto.setTotalUsers(20);

        dto.setActiveUsers(18);

        dto.setTotalAccounts(30);

        dto.setTotalTransactions(500);

        dto.setTotalTransactionAmount(900000);

        assertNotNull(dto);

        assertEquals(
                20,
                dto.getTotalUsers()
        );

        assertEquals(
                18,
                dto.getActiveUsers()
        );

        assertEquals(
                30,
                dto.getTotalAccounts()
        );

        assertEquals(
                500,
                dto.getTotalTransactions()
        );

        assertEquals(
                900000,
                dto.getTotalTransactionAmount()
        );
    }
}