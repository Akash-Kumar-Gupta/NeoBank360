package com.infy.neobank360.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.neobank360.auth.dto.LoginRequest;
import com.infy.neobank360.auth.dto.RegisterRequest;
import com.infy.neobank360.security.JwtUtil;
import com.infy.neobank360.user.User;
import com.infy.neobank360.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository repo;
    private final JwtUtil jwt;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository repo, JwtUtil jwt) {
        this.repo = repo;
        this.jwt = jwt;
    }

    public void register(RegisterRequest req) {
        if (repo.existsByEmail(req.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setFullName(req.fullName());
        user.setEmail(req.email());
        user.setPasswordHash(encoder.encode(req.password()));
        repo.save(user);
    }

    public LoginResponse login(LoginRequest req) {
        User user = repo.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new LoginResponse(jwt.generateToken(user));
    }
}