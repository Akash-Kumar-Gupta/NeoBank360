package com.infy.neobank360.loan;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.infy.neobank360.user.User;

import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationService 
{

    private final LoanApplicationRepository repository;
    private final LoanProductRepository productRepository;

    public LoanApplicationService(
            LoanApplicationRepository repository,
            LoanProductRepository productRepository
    ) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public LoanApplicationResponseDTO apply(LoanApplicationRequestDTO dto) 
    {

        // ✅ 1. Get logged-in user directly from SecurityContext
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = user.getId();

        // ✅ DEBUG (optional)
        System.out.println("LOGGED IN USER EMAIL: " + user.getEmail());

        // ✅ 2. Fetch Loan Product
        LoanProduct product = productRepository.findById(dto.loanProductId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ 3. VALIDATION

        // Amount check
        if (dto.amount < product.getMinAmount() || dto.amount > product.getMaxAmount()) {
            throw new RuntimeException("Invalid amount range");
        }

        // Tenure check
        if (!product.getAllowedTenures().contains(dto.tenure.toString())) {
            throw new RuntimeException("Invalid tenure");
        }

        // Duplicate check
        Optional<LoanApplication> existing =
                repository.findByUserIdAndLoanProductIdAndStatus(
                        userId,
                        dto.loanProductId,
                        LoanApplication.Status.PENDING
                );

        if (existing.isPresent()) {
            throw new RuntimeException("Duplicate application exists");
        }

        // ✅ 4. SAVE APPLICATION
        LoanApplication app = new LoanApplication();
        app.setUserId(userId);
        app.setLoanProductId(dto.loanProductId);
        app.setRequestedAmount(dto.amount);
        app.setRequestedTenureMonths(dto.tenure);

        LoanApplication saved = repository.save(app);

        // ✅ 5. RESPONSE
        LoanApplicationResponseDTO res = new LoanApplicationResponseDTO();
        res.id = saved.getId();
        res.status = saved.getStatus().name();
        res.amount = saved.getRequestedAmount();
        res.tenure = saved.getRequestedTenureMonths();

        return res;
    }
    

    public List<LoanApplication> getAllApplications() 
    {
        return repository.findAll();
    }
    

    public List<LoanApplication> getApplicationsByUserId(Long userId)
    {
        return repository.findByUserId(userId);
    }
}

