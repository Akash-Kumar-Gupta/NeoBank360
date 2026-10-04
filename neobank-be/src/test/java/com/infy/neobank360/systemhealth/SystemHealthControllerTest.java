package com.infy.neobank360.systemhealth;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SystemHealthControllerTest
{

    @Test
    void testSystemHealthDTO()
    {

        SystemHealthDTO dto =
                new SystemHealthDTO();

        dto.setDatabaseStatus("ONLINE");

        dto.setSecurityStatus("ACTIVE");

        dto.setTransactionEngine("STABLE");

        dto.setPendingRequests(3);

        dto.setPendingLoans(5);

        dto.setFraudAlerts(0);

        dto.setServerUptimeSeconds(120);

        assertNotNull(dto);

        assertEquals(
                "ONLINE",
                dto.getDatabaseStatus()
        );

        assertEquals(
                "ACTIVE",
                dto.getSecurityStatus()
        );

        assertEquals(
                "STABLE",
                dto.getTransactionEngine()
        );

        assertEquals(
                3,
                dto.getPendingRequests()
        );

        assertEquals(
                5,
                dto.getPendingLoans()
        );

        assertEquals(
                0,
                dto.getFraudAlerts()
        );

        assertEquals(
                120,
                dto.getServerUptimeSeconds()
        );
    }
}