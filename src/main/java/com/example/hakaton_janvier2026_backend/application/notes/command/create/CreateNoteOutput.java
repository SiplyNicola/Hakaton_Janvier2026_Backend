package com.example.hakaton_janvier2026_backend.application.notes.command.create;

import java.time.LocalDateTime;

public class CreateNoteOutput {
    public int id;
    public String title;
    public String content_markdown;
    public LocalDateTime created_at;
    public long size_bytes;
    public int word_count;
}
