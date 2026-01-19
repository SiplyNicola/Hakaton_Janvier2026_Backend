package com.example.hakaton_janvier2026_backend.application.notes.command.create;

import jakarta.validation.constraints.NotBlank;

public class CreateNoteInput {
    @NotBlank
    public String title;
    public String content_markdown;
    @NotBlank
    public int owner_id;
    @NotBlank
    public Integer folder_id;
}
