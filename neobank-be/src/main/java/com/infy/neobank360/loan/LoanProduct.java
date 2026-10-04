package com.infy.neobank360.loan;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_products")
public class LoanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Double minAmount;

    private Double maxAmount;

    private Double annualInterestRate;

    private String allowedTenures; // e.g. "12,24,36"

    private LocalDateTime createdAt = LocalDateTime.now();

    // ✅ NEW FIELD ADDED
    private boolean active = true;

    // ✅ GETTERS & SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getMinAmount() { return minAmount; }
    public void setMinAmount(Double minAmount) { this.minAmount = minAmount; }

    public Double getMaxAmount() { return maxAmount; }
    public void setMaxAmount(Double maxAmount) { this.maxAmount = maxAmount; }

    public Double getAnnualInterestRate() { return annualInterestRate; }
    public void setAnnualInterestRate(Double annualInterestRate) { this.annualInterestRate = annualInterestRate; }

    public String getAllowedTenures() { return allowedTenures; }
    public void setAllowedTenures(String allowedTenures) { this.allowedTenures = allowedTenures; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    // ✅ NEW METHODS (VERY IMPORTANT)

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
