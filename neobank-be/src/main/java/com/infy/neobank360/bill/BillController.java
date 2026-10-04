package com.infy.neobank360.bill;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.infy.neobank360.user.User;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService service;

    public BillController(BillService service) {
        this.service = service;
    }

    // ✅ CREATE BILL (uses logged-in user)
    @PostMapping
    public Bill create(@RequestBody Bill bill, @AuthenticationPrincipal User user) {

        return service.create(user.getId(), bill);
    }

    // ✅ GET ALL BILLS (IMPORTANT FIX ✅)
    @GetMapping
    public List<Bill> getAll(@AuthenticationPrincipal User user) {

        return service.getAll(user.getId());
    }

    // ✅ MARK AS PAID
    @PutMapping("/{id}/pay")
    public Bill pay(@PathVariable Long id) {
        return service.markPaid(id);
    }
}

