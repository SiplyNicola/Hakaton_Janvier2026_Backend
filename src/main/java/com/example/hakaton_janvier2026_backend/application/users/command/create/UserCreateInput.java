package com.example.hakaton_janvier2026_backend.application.users.command.create;

import jakarta.validation.constraints.NotBlank;

public class UserCreateInput {
    @NotBlank
    public String username;
    @NotBlank
    public String password;
}
