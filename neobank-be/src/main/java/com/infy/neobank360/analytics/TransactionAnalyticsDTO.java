package com.infy.neobank360.analytics;


import java.util.List;

public class TransactionAnalyticsDTO {

    private List<Double> dailyInflow;
    private List<Double> dailyOutflow;
    private double averageTicketSize;
    private double totalInflow;
    private double totalOutflow;
	public List<Double> getDailyInflow() {
		return dailyInflow;
	}
	public void setDailyInflow(List<Double> dailyInflow) {
		this.dailyInflow = dailyInflow;
	}
	public List<Double> getDailyOutflow() {
		return dailyOutflow;
	}
	public void setDailyOutflow(List<Double> dailyOutflow) {
		this.dailyOutflow = dailyOutflow;
	}
	public double getAverageTicketSize() {
		return averageTicketSize;
	}
	public void setAverageTicketSize(double averageTicketSize) {
		this.averageTicketSize = averageTicketSize;
	}
	public double getTotalInflow() {
		return totalInflow;
	}
	public void setTotalInflow(double totalInflow) {
		this.totalInflow = totalInflow;
	}
	public double getTotalOutflow() {
		return totalOutflow;
	}
	public void setTotalOutflow(double totalOutflow) {
		this.totalOutflow = totalOutflow;
	}

}
