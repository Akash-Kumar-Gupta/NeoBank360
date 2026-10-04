package com.infy.neobank360.logging;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.infy.neobank360.security.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditLoggingFilter extends OncePerRequestFilter {

    @Autowired
    private SystemAuditLogRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        String endpoint = request.getRequestURI();
        String method = request.getMethod();

        Long userId = null;
        String errorMessage = null;

        // ✅ Extract user from JWT
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                userId = jwtUtil.extractUserId(token);
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        try {
            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            throw ex;

        } finally {

            long executionTime = System.currentTimeMillis() - startTime;

            SystemAuditLog log = new SystemAuditLog();
            log.setEndpoint(endpoint);
            log.setHttpMethod(method);
            log.setResponseStatus(response.getStatus());
            log.setExecutionTimeMs(executionTime);
            log.setActingUserId(userId);
            log.setErrorMessage(response.getStatus() >= 400 ? errorMessage : null);

            repo.save(log);
        }
    }
}
