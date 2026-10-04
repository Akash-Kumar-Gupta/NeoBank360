package com.infy.neobank360.logging;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemAuditLogRepository extends JpaRepository<SystemAuditLog, Long> 
{

}
