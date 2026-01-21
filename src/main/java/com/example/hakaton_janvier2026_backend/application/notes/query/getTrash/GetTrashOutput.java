package com.example.hakaton_janvier2026_backend.application.notes.query.getTrash;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetTrashOutput {
    public List<FolderNode> folders = new ArrayList<>();
    public List<GetTrashOutput.NoteNode> notes = new ArrayList<>();

    public static class FolderNode {
        public int id;
        public String name;
        public LocalDateTime deletedAt;
        public List<GetTrashOutput.FolderNode> subFolders = new ArrayList<>();
        public List<GetTrashOutput.NoteNode> notes = new ArrayList<>();
    }

    public static class NoteNode {
        public int id;
        public String title;
        public String content_markdown;
        public LocalDateTime deletedAt;
    }
}
