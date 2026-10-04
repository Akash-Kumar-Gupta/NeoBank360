package com.infy.neobank360.auth.dto;


import jakarta.validation.constraints.*;

public record LoginRequest(
    @Email @NotBlank String email,
    @NotBlank String password
) {}
