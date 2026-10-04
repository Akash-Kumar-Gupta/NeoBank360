package com.infy.neobank360.systemhealth;


import java.lang.management.ManagementFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemHealthService {

    @Autowired
    private SystemHealthRepository repo;

    public SystemHealthDTO getSystemHealth() {

        SystemHealthDTO dto =
                new SystemHealthDTO();

        dto.setDatabaseStatus("ONLINE");

        dto.setSecurityStatus("ACTIVE");

        dto.setTransactionEngine("STABLE");

        dto.setPendingRequests(
                repo.getPendingRequests()
        );

        dto.setPendingLoans(
                repo.getPendingLoans()
        );
        
        long uptimeMillis =
                ManagementFactory
                    .getRuntimeMXBean()
                    .getUptime();

        long uptimeSeconds =
                uptimeMillis / 1000;

        dto.setServerUptimeSeconds(uptimeSeconds);
        

        return dto;
    }
}