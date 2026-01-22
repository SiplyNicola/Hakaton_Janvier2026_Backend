package com.example.hakaton_janvier2026_backend.application.notes.command.switchmode;

import jakarta.validation.constraints.NotNull;

public class SwitchNoteModeInput {
    public int id;

    @NotNull
    public Boolean is_write_mode;
}