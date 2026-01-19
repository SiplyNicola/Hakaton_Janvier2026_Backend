package com.example.hakaton_janvier2026_backend.application.notes.command.create;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.users.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class CreateNoteHandler {

    private final INoteRepository noteRepository;
    private final IUserRepository userRepository;
    private final IFolderRepository folderRepository;
    private final ModelMapper modelMapper;

    public CreateNoteHandler(INoteRepository noteRepository, IUserRepository userRepository, IFolderRepository folderRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.modelMapper = modelMapper;
    }


    public CreateNoteOutput handle(CreateNoteInput input) {
        //  Préparation de l'entité DB
        DbNote dbNote = new DbNote();
        dbNote.title = input.title;
        dbNote.content_markdown = input.content_markdown;
        dbNote.created_at = LocalDateTime.now();

        //  Calcul des métadonnées (Palier Zombie)
        dbNote.char_count = input.content_markdown.length();
        dbNote.word_count = input.content_markdown.split("\\s+").length;
        dbNote.line_count = input.content_markdown.split("\r\n|\r|\n").length;
        dbNote.size_bytes = input.content_markdown.getBytes(StandardCharsets.UTF_8).length;

        //  Liaison des relations (Clés étrangères)
        dbNote.owner = userRepository.findById(input.owner_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (input.folder_id != null) {
            dbNote.folder = folderRepository.findById(input.folder_id).orElse(null);
        }

        // Sauvegarde
        DbNote savedNote = noteRepository.save(dbNote);

        return modelMapper.map(savedNote, CreateNoteOutput.class);
    }

}
