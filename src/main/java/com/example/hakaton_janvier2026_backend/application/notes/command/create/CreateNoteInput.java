package com.example.hakaton_janvier2026_backend.application.notes.command.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateNoteInput {
    @NotBlank(message = "Le titre est obligatoire")
    public String title;

    public String content_markdown;

    @NotNull(message = "L'ID du propriétaire est obligatoire")
    public Integer owner_id; // Changé de int à Integer pour la validation

    public Integer folder_id;
}
