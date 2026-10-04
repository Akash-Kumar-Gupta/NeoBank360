package com.infy.neobank360.budget;

import java.time.YearMonth;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.infy.neobank360.user.User;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) 
    {
        this.service = service;
    }

    @PostMapping
    public Budget create(@RequestBody Budget budget,
                         @AuthenticationPrincipal User user) 
    {

        return service.create(user.getId(), budget);
    }
    
    @GetMapping("/{month}")
    public List<BudgetSummaryDTO> getSummary(
            @PathVariable String month,
            @AuthenticationPrincipal com.infy.neobank360.user.User user) 
    {

        YearMonth ym = YearMonth.parse(month);
        
        return service.getSummary(user.getId(), ym);
    }
}
