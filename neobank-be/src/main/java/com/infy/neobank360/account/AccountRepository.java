package com.infy.neobank360.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.neobank360.user.User;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser(User user);

    List<Account> findByUserId(Long userId);

    boolean existsByAccountNumber(String accountNumber);
    
    Optional<Account> findByAccountNumber(String accountNumber);
}