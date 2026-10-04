package com.infy.neobank360.insights;

import com.infy.neobank360.user.User;
import com.infy.neobank360.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insights")
public class InsightsController {

    private final InsightsService service;
    private final UserRepository userRepository;

    public InsightsController(InsightsService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @GetMapping
    public FinancialInsightsDTO getInsights(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return service.getInsights(user.getId());
    }
    
}