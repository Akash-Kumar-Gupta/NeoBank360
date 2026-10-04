package com.infy.neobank360.loan;

public class LoanApplicationRequestDTO
{

    public Long loanProductId;
    public Double amount;
    public Integer tenure;
    
    
	public Long getLoanProductId() {
		return loanProductId;
	}
	public void setLoanProductId(Long loanProductId) {
		this.loanProductId = loanProductId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getTenure() {
		return tenure;
	}
	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}
}