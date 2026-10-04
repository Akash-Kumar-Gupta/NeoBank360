package com.infy.neobank360.systemhealth;

public class SystemHealthDTO {

    private String databaseStatus;
    private String securityStatus;
    private String transactionEngine;

    private long pendingRequests;
    private long pendingLoans;
    private long fraudAlerts;
    private long serverUptimeSeconds;

    // GETTERS & SETTERS

    public String getDatabaseStatus() {
        return databaseStatus;
    }

    public void setDatabaseStatus(String databaseStatus) {
        this.databaseStatus = databaseStatus;
    }

    public String getSecurityStatus() {
        return securityStatus;
    }

    public void setSecurityStatus(String securityStatus) {
        this.securityStatus = securityStatus;
    }

    public String getTransactionEngine() {
        return transactionEngine;
    }

    public void setTransactionEngine(String transactionEngine) {
        this.transactionEngine = transactionEngine;
    }

    public long getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(long pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public long getPendingLoans() {
        return pendingLoans;
    }

    public void setPendingLoans(long pendingLoans) {
        this.pendingLoans = pendingLoans;
    }

    public long getFraudAlerts() {
        return fraudAlerts;
    }

    public void setFraudAlerts(long fraudAlerts) {
        this.fraudAlerts = fraudAlerts;
    }

	public long getServerUptimeSeconds() {
		return serverUptimeSeconds;
	}

	public void setServerUptimeSeconds(long serverUptimeSeconds) {
		this.serverUptimeSeconds = serverUptimeSeconds;
	}
    
}