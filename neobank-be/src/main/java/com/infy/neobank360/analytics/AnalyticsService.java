package com.infy.neobank360.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository repo;

    public AdminAnalyticsDTO getAnalytics() {

        AdminAnalyticsDTO dto = new AdminAnalyticsDTO();

        dto.setTotalUsers(repo.getTotalUsers());
        dto.setActiveUsers(repo.getActiveUsers());
        dto.setTotalAccounts(repo.getTotalAccounts());
        dto.setTotalTransactions(repo.getTotalTransactions());
        dto.setTotalTransactionAmount(repo.getTotalTransactionAmount());
        dto.setTransactions(repo.getAllTransactions());

        return dto;
    }

    private java.time.Instant getStartDate(String timeframe) {

        switch (timeframe) {
            case "7d":
                return java.time.Instant.now()
                        .minus(java.time.Duration.ofDays(7));

            case "30d":
                return java.time.Instant.now()
                        .minus(java.time.Duration.ofDays(30));

            case "YTD":
                return java.time.LocalDate.now()
                        .withDayOfYear(1)
                        .atStartOfDay(java.time.ZoneId.systemDefault())
                        .toInstant();

            default:
                return java.time.Instant.now()
                        .minus(java.time.Duration.ofDays(7));
        }
    }

    public TransactionAnalyticsDTO getTransactionAnalytics(String timeframe) {

        java.time.Instant startInstant = getStartDate(timeframe);

        var records = repo.getDailyTransactionStats(startInstant);

        java.util.Map<java.time.LocalDate, Double> inflowMap = new java.util.HashMap<>();
        java.util.Map<java.time.LocalDate, Double> outflowMap = new java.util.HashMap<>();

        for (Object[] row : records) {

            java.time.Instant instant = (java.time.Instant) row[0];

            java.time.LocalDate date = instant
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();

            double credit = ((Number) row[1]).doubleValue();
            double debit = ((Number) row[2]).doubleValue();

            inflowMap.put(date, credit);
            outflowMap.put(date, debit);
        }

        java.util.List<Double> inflow = new java.util.ArrayList<>();
        java.util.List<Double> outflow = new java.util.ArrayList<>();

        java.time.LocalDate startDate = startInstant
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        java.time.LocalDate today = java.time.LocalDate.now();

        double totalInflow = 0;
        double totalOutflow = 0;
        int totalCount = 0;

        while (!startDate.isAfter(today)) {

            double in = inflowMap.getOrDefault(startDate, 0.0);
            double out = outflowMap.getOrDefault(startDate, 0.0);

            inflow.add(in);
            outflow.add(out);

            totalInflow += in;
            totalOutflow += out;
            totalCount++;

            startDate = startDate.plusDays(1);
        }

        TransactionAnalyticsDTO dto = new TransactionAnalyticsDTO();
        dto.setDailyInflow(inflow);
        dto.setDailyOutflow(outflow);
        dto.setTotalInflow(totalInflow);
        dto.setTotalOutflow(totalOutflow);

        dto.setAverageTicketSize(
                totalCount == 0 ? 0 : (totalInflow + totalOutflow) / totalCount
        );

        return dto;
    }


    public LoanAnalyticsDTO getLoanAnalytics(String timeframe) {

        var distributionData = repo.getLoanDistribution();

        java.util.Map<String, Long> distribution = new java.util.HashMap<>();

        for (Object[] row : distributionData) {
            distribution.put(row[0].toString(), ((Number) row[1]).longValue());
        }

        long npaCount = repo.getNpaCount();
        long total = repo.getTotalLoans();

        LoanAnalyticsDTO dto = new LoanAnalyticsDTO();
        dto.setLoanDistribution(distribution);
        dto.setNpaCount(npaCount);
        dto.setNpaRatio(total == 0 ? 0 : (double) npaCount / total);

        return dto;
    }
}
