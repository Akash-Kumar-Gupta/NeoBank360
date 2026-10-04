package com.infy.neobank360.loan;

public class LoanProductDTO {

    public Long id;
    public String productName;
    public Double minAmount;
    public Double maxAmount;
    public Double annualInterestRate;
    public String allowedTenures;

    // ✅ ADD THIS FIELD
    public boolean active;

    // ✅ GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(Double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public String getAllowedTenures() {
        return allowedTenures;
    }

    public void setAllowedTenures(String allowedTenures) {
        this.allowedTenures = allowedTenures;
    }

    // ✅ NEW METHODS

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
