package com.infy.neobank360.loan;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanProductService {

    private final LoanProductRepository repository;

    public LoanProductService(LoanProductRepository repository) {
        this.repository = repository;
    }

    // ✅ Toggle Active/Inactive
    public void toggleStatus(Long id) {
        LoanProduct product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Product not found"));

        product.setActive(!product.isActive());
        repository.save(product);
    }

    // ✅ Create Product
    public LoanProductDTO create(LoanProductDTO dto) {

        LoanProduct entity = new LoanProduct();

        entity.setProductName(dto.productName);
        entity.setMinAmount(dto.minAmount);
        entity.setMaxAmount(dto.maxAmount);
        entity.setAnnualInterestRate(dto.annualInterestRate);
        entity.setAllowedTenures(dto.allowedTenures);

        // ✅ IMPORTANT: Set default status
        entity.setActive(true);

        LoanProduct saved = repository.save(entity);

        return mapToDTO(saved);
    }

    // ✅ Get All Products
    public List<LoanProductDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Mapping Entity → DTO
    private LoanProductDTO mapToDTO(LoanProduct entity) {

        LoanProductDTO dto = new LoanProductDTO();

        dto.id = entity.getId();
        dto.productName = entity.getProductName();
        dto.minAmount = entity.getMinAmount();
        dto.maxAmount = entity.getMaxAmount();
        dto.annualInterestRate = entity.getAnnualInterestRate();
        dto.allowedTenures = entity.getAllowedTenures();

        // ✅ IMPORTANT: Include active field
        dto.active = entity.isActive();

        return dto;
    }
}
