package com.infy.neobank360.bill;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    private final BillRepository repo;
    private final com.infy.neobank360.reward.RewardService rewardService;

    public BillService(BillRepository repo,
                       com.infy.neobank360.reward.RewardService rewardService) {
        this.repo = repo;
        this.rewardService = rewardService;
    }

    // ✅ CREATE BILL
    public Bill create(Long userId, Bill bill) {

        bill.setUserId(userId);
        bill.setStatus(BillStatus.PENDING);

        return repo.save(bill);
    }

    // ✅ GET ALL BILLS
    public List<Bill> getAll(Long userId) {
        return repo.findByUserId(userId);
    }

    // ✅ MARK AS PAID
    public Bill markPaid(Long billId) {

        Bill bill = repo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setStatus(BillStatus.PAID);
        bill.setPaidDate(LocalDate.now());

        Bill saved = repo.save(bill);

        // ✅ REWARD LOGIC
        if (bill.getPaidDate().isBefore(bill.getDueDate())
                || bill.getPaidDate().isEqual(bill.getDueDate())) {

            rewardService.addPoints(
                    bill.getUserId(),
                    10,
                    "Paid bill on time: " + bill.getName()
            );
        }

        return saved;
    }
}
