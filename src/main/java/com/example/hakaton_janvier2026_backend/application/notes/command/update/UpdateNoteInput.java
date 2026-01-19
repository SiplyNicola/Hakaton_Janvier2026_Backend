package com.example.hakaton_janvier2026_backend.application.notes.command.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateNoteInput {
    @NotNull
    @Positive
    public int id;

    public String title;
    public String content_markdown;
    public Integer folder_id;
}
