package com.infy.neobank360.auth.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
    @NotBlank String fullName,
    @Email @NotBlank String email,
    @Size(min = 8) String password
) {}
