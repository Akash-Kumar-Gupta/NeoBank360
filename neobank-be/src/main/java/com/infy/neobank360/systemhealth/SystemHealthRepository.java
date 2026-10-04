package com.infy.neobank360.systemhealth;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infy.neobank360.user.User;

@Repository
public interface SystemHealthRepository
        extends JpaRepository<User, Long> {

    // Pending account requests
    @Query("""
        SELECT COUNT(a)
        FROM AccountOpeningRequest a
        WHERE a.status = 'PENDING'
    """)
    long getPendingRequests();

    // Pending loans
    @Query("""
        SELECT COUNT(l)
        FROM LoanApplication l
        WHERE l.status = 'PENDING'
    """)
    long getPendingLoans();
}