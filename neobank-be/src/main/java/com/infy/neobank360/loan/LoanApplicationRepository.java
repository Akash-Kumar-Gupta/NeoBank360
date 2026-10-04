package com.infy.neobank360.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    // ✅ Prevent duplicate PENDING applications
    Optional<LoanApplication> findByUserIdAndLoanProductIdAndStatus(
            Long userId,
            Long productId,
            LoanApplication.Status status
    );

    // ✅ Get all applications of a user (for "My Applications" page)
    List<LoanApplication> findByUserId(Long userId);

    // ✅ Get only PENDING applications (optional filter for admin)
    List<LoanApplication> findByStatus(LoanApplication.Status status);
}