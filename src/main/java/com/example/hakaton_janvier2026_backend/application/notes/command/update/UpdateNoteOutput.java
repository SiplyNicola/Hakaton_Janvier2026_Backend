package com.example.hakaton_janvier2026_backend.application.notes.command.update;

import java.time.LocalDateTime;

public class UpdateNoteOutput {
    public int id;
    public String title;
    public String content_markdown;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
    public long size_bytes;
    public int line_count;
    public int word_count;
    public int char_count;
}
