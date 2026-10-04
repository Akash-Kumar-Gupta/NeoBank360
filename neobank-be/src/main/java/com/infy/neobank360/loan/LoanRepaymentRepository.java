package com.infy.neobank360.loan;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment, Long> {

    // ✅ GET FULL SCHEDULE (USED IN UI)
    List<LoanRepayment> findByLoanAccountIdOrderByInstallmentNumber(Long loanAccountId);

    // ✅ OPTIONAL: GET ONLY PENDING EMIs
    List<LoanRepayment> findByLoanAccountIdAndPaymentStatusOrderByInstallmentNumber(
            Long loanAccountId,
            LoanRepayment.Status status
    );

    // ✅ OPTIONAL: GET NEXT EMI TO BE PAID
    Optional<LoanRepayment> findFirstByLoanAccountIdAndPaymentStatusOrderByInstallmentNumber(
            Long loanAccountId,
            LoanRepayment.Status status
    );
}