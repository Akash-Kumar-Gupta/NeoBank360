package com.infy.neobank360.loan;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplication
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long loanProductId;

    private Double requestedAmount;

    private Integer requestedTenureMonths;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;


    private LocalDateTime appliedAt = LocalDateTime.now();

    // ✅ ENUM
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
    

    // ✅ getters/setters
    
    public Long getId() { return id; }

	public LocalDateTime getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(LocalDateTime appliedAt) {
		this.appliedAt = appliedAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLoanProductId() {
		return loanProductId;
	}

	public void setLoanProductId(Long loanProductId) {
		this.loanProductId = loanProductId;
	}

	public Double getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(Double requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public Integer getRequestedTenureMonths() {
		return requestedTenureMonths;
	}

	public void setRequestedTenureMonths(Integer requestedTenureMonths) {
		this.requestedTenureMonths = requestedTenureMonths;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}