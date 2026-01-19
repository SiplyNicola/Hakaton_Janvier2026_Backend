package com.example.hakaton_janvier2026_backend.application.notes.command.update;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class UpdateNoteHandler {

    private final INoteRepository noteRepository;
    private final IFolderRepository folderRepository;
    private final ModelMapper modelMapper;

    public UpdateNoteHandler(INoteRepository noteRepository, IFolderRepository folderRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.folderRepository = folderRepository;
        this.modelMapper = modelMapper;
    }

    public UpdateNoteOutput handle(UpdateNoteInput input) {
        // 1. Récupération de la note en base de données
        DbNote dbNote = noteRepository.findById(input.id)
                .orElseThrow(() -> new RuntimeException("Note introuvable"));

        // 2. Mise à jour du contenu
        dbNote.title = input.title;
        dbNote.content_markdown = input.content_markdown;

        // 3. Calcul des métadonnées en temps réel (Palier Zombie)
        String content = (input.content_markdown != null) ? input.content_markdown : "";

        dbNote.updated_at = LocalDateTime.now();
        dbNote.char_count = content.length();
        dbNote.size_bytes = content.getBytes(StandardCharsets.UTF_8).length;

        // Calcul des lignes
        dbNote.line_count = content.isEmpty() ? 0 : content.split("\\r?\\n").length;

        // Calcul des mots (séparation par espaces/tabulations) [cite: 70]
        dbNote.word_count = content.trim().isEmpty() ? 0 : content.trim().split("\\s+").length;

        // 4. Mise à jour optionnelle du dossier
        if (input.folder_id != null) {
            dbNote.folder = folderRepository.findById(input.folder_id).orElse(null);
        }

        // 5. Sauvegarde dans MySQL 8
        DbNote saved = noteRepository.save(dbNote);

        // 6. Mapping vers l'Output
        return modelMapper.map(saved, UpdateNoteOutput.class);
    }
}
