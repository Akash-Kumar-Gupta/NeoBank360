package com.infy.neobank360.reward;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService service;

    public RewardController(RewardService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public List<Reward> getAll(@PathVariable Long userId) {
        return service.getAll(userId);
    }
}