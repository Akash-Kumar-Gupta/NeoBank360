package com.infy.neobank360.loan;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.infy.neobank360.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanApplicationController {

    private final LoanApplicationService service;
    private final LoanDecisionService decisionService;

    // ✅ Constructor
    public LoanApplicationController(
            LoanApplicationService service,
            LoanDecisionService decisionService
    ) {
        this.service = service;
        this.decisionService = decisionService;
    }

    // ✅ 1. USER APPLY LOAN
    @PostMapping("/apply")
    public LoanApplicationResponseDTO apply(@RequestBody LoanApplicationRequestDTO dto) {
        return service.apply(dto);
    }

    // ✅ ✅ 2. ADMIN FETCH ALL APPLICATIONS (NEW)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<LoanApplication> getAllApplications() {
        return service.getAllApplications();
    }

    // ✅ 3. ADMIN DECISION (APPROVE / REJECT)
    @PutMapping("/{id}/decision")
    @PreAuthorize("hasRole('ADMIN')")
    public LoanApplication decide(
            @PathVariable Long id,
            @RequestBody LoanDecisionDTO dto
    ) {
        return decisionService.decide(id, dto);
    }
    

    @GetMapping("/my")
    public List<LoanApplication> getMyApplications() 
    {
        User user = (User) org.springframework.security.core.context.SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        Long userId = user.getId();

        return service.getApplicationsByUserId(userId);
    }
}