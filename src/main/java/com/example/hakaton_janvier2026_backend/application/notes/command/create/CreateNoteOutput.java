package com.example.hakaton_janvier2026_backend.application.notes.command.create;

import java.time.LocalDateTime;

public class CreateNoteOutput {
    public int id;
    public int owner_id;
    public int folder_id;
    public String title;
    public String content_markdown;
}
