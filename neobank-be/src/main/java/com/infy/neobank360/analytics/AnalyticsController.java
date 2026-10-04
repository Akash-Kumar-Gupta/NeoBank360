package com.infy.neobank360.analytics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/analytics")
@CrossOrigin(origins = "http://localhost:4200")
public class AnalyticsController {

    @Autowired
    private AnalyticsService service;

    @GetMapping
    public AdminAnalyticsDTO getAnalytics() 
    {
        return service.getAnalytics();
    }
    
    // Transaction Analytics
    @GetMapping("/transactions")
    public TransactionAnalyticsDTO getTransactionAnalytics(
            @RequestParam String timeframe) {

        return service.getTransactionAnalytics(timeframe);
    }

    // Loan Analytics
    @GetMapping("/loans")
    public LoanAnalyticsDTO getLoanAnalytics(
            @RequestParam String timeframe) {

        return service.getLoanAnalytics(timeframe);
    }
}
