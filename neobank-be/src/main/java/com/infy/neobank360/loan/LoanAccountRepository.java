package com.infy.neobank360.loan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanAccountRepository extends JpaRepository<LoanAccount, Long> 
{
	
	Optional<LoanAccount> findByLoanApplicationId(Long loanApplicationId);
}
