package com.infy.neobank360.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

import com.infy.neobank360.logging.AuditLoggingFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwt;

    @Autowired
    private AuditLoggingFilter auditLoggingFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .cors(Customizer.withDefaults())

            .authorizeHttpRequests(auth -> auth

                // ✅ Public APIs
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/loans/products/**").permitAll()

                // ✅ Allow OPTIONS (important for frontend)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ ADMIN APIs

                .requestMatchers("/api/admin/analytics/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // ✅ CUSTOMER APIs
                .requestMatchers(HttpMethod.POST, "/api/loan-repayments/pay")
                    .hasRole("CUSTOMER")

                .requestMatchers("/api/loan-repayments/**")
                    .hasRole("CUSTOMER")

                // ✅ All others require authentication
                .anyRequest().authenticated()
            )

            // ✅ ORDER OF FILTERS (VERY IMPORTANT)
            .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class)

            // ✅ ADD AUDIT FILTER AFTER JWT (BEST PRACTICE)
            .addFilterAfter(auditLoggingFilter, JwtAuthFilter.class);

        return http.build();
    }
}
