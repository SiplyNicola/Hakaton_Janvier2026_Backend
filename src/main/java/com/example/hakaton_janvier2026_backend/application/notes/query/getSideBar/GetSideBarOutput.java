package com.example.hakaton_janvier2026_backend.application.notes.query.getSideBar;

import java.util.ArrayList;
import java.util.List;

public class GetSideBarOutput {
    public List<FolderNode> folders = new ArrayList<>();
    public List<NoteNode> notes = new ArrayList<>();

    public static class FolderNode {
        public int id;
        public String name;
        public List<FolderNode> subFolders = new ArrayList<>();
        public List<NoteNode> notes = new ArrayList<>();
    }

    public static class NoteNode {
        public int id;
        public String title;
        public String content_markdown;
    }
}
