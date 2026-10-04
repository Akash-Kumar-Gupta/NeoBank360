package com.infy.neobank360.loan;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans/products")
@CrossOrigin(origins = "http://localhost:4200")
public class LoanProductController 
{

    private final LoanProductService service;

    public LoanProductController(LoanProductService service) 
    {
        this.service = service;
    }

    @PostMapping
    public LoanProductDTO create(@RequestBody LoanProductDTO dto) 
    {
        return service.create(dto);
    }

    @GetMapping
    public List<LoanProductDTO> getAll() 
    {
        return service.getAll();
    }

    // ✅ ADD THIS METHOD
    @PatchMapping("/{id}/toggle")
    public void toggle(@PathVariable Long id) 
    {
        service.toggleStatus(id);
    }
}
