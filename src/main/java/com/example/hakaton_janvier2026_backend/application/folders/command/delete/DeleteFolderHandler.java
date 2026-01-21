package com.example.hakaton_janvier2026_backend.application.folders.command.delete;

import com.example.hakaton_janvier2026_backend.application.exceptions.ParentFolderNotFoundException;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DeleteFolderHandler {

    private final IFolderRepository folderRepository;
    private final INoteRepository noteRepository;

    public DeleteFolderHandler(IFolderRepository folderRepository, INoteRepository noteRepository) {
        this.folderRepository = folderRepository;
        this.noteRepository = noteRepository;
    }

    @Transactional
    public DeleteFolderOutput handle(DeleteFolderInput input) {
        // 1. Récupérer le dossier racine de l'opération
        DbFolder folder = folderRepository.findById(input.getFolderId())
                .orElseThrow(ParentFolderNotFoundException::new);

        // 2. Lancer la suppression récursive de tout le contenu
        deleteRecursive(folder);

        // 3. Retourner la confirmation
        return DeleteFolderOutput.builder()
                .deletedFolderId(input.getFolderId())
                .success(true)
                .build();
    }
    
    private void deleteRecursive(DbFolder folder) {
        // A. Supprimer toutes les notes rattachées à ce dossier
        List<DbNote> notes = noteRepository.findAllByFolderId(folder.id);
        for (DbNote note : notes) {
            noteRepository.delete(note);
        }

        // B. Traiter récursivement les sous-dossiers
        List<DbFolder> subFolders = folderRepository.findAllByParentFolderId(folder.id);
        for (DbFolder subFolder : subFolders) {
            deleteRecursive(subFolder);
        }

        // C. Enfin, supprimer le dossier lui-même une fois vidé
        folderRepository.delete(folder);
    }
}