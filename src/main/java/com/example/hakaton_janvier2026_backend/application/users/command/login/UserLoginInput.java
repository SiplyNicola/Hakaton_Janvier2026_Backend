package com.example.hakaton_janvier2026_backend.application.users.command.login;

import jakarta.validation.constraints.NotBlank;

public class UserLoginInput {
    @NotBlank
    public String username;
    @NotBlank
    public String password;
}
