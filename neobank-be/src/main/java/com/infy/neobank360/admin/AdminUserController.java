package com.infy.neobank360.admin;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.neobank360.user.User;
import com.infy.neobank360.user.UserRepository;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserRepository userRepo;

    public AdminUserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> allUsers() {
        return userRepo.findAll();
    }

    @PutMapping("/{id}/activate")
    public void activate(@PathVariable Long id) {
        User user = userRepo.findById(id).orElseThrow();
        user.setActive(true);
        userRepo.save(user);
    }

    @PutMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        User user = userRepo.findById(id).orElseThrow();
        user.setActive(false);
        userRepo.save(user);
    }
}
