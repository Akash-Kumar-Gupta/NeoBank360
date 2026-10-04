package com.infy.neobank360.analytics;


import java.util.Map;

public class LoanAnalyticsDTO {

    private Map<String, Long> loanDistribution;
    private long npaCount;
    private double npaRatio;
	public Map<String, Long> getLoanDistribution() {
		return loanDistribution;
	}
	public void setLoanDistribution(Map<String, Long> loanDistribution) {
		this.loanDistribution = loanDistribution;
	}
	public long getNpaCount() {
		return npaCount;
	}
	public void setNpaCount(long npaCount) {
		this.npaCount = npaCount;
	}
	public double getNpaRatio() {
		return npaRatio;
	}
	public void setNpaRatio(double npaRatio) {
		this.npaRatio = npaRatio;
	}

}