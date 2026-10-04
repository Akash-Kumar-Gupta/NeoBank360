package com.infy.neobank360.reward;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardService {

    private final RewardRepository repo;

    public RewardService(RewardRepository repo) {
        this.repo = repo;
    }

    public void addPoints(Long userId, int points, String desc) {

        Reward r = new Reward();
        r.setUserId(userId);
        r.setPoints(points);
        r.setDescription(desc);
        r.setCreatedAt(LocalDateTime.now());

        repo.save(r);
    }

    public List<Reward> getAll(Long userId) {
        return repo.findByUserId(userId);
    }
}
