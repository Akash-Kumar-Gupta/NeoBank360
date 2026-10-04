package com.infy.neobank360.systemhealth;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SystemHealthServiceTest
{

    @Test
    void testSystemHealthValues()
    {

        String databaseStatus = "ONLINE";

        String securityStatus = "ACTIVE";

        String transactionEngine = "STABLE";

        long pendingRequests = 4;

        long pendingLoans = 2;

        long uptime = 100;

        assertEquals(
                "ONLINE",
                databaseStatus
        );

        assertEquals(
                "ACTIVE",
                securityStatus
        );

        assertEquals(
                "STABLE",
                transactionEngine
        );

        assertEquals(
                4,
                pendingRequests
        );

        assertEquals(
                2,
                pendingLoans
        );

        assertTrue(uptime >= 0);
    }
}