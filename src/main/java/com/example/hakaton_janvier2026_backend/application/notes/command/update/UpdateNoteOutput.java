package com.example.hakaton_janvier2026_backend.application.notes.command.update;

import java.time.LocalDateTime;

public class UpdateNoteOutput {
    public int id;
    public String title;
    public String content_markdown;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}
