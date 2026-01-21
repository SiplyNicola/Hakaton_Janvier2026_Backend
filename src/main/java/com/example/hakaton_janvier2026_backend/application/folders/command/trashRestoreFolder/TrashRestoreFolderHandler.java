package com.example.hakaton_janvier2026_backend.application.folders.command.trashRestoreFolder;

import com.example.hakaton_janvier2026_backend.application.exceptions.ParentFolderNotFoundException;
import com.example.hakaton_janvier2026_backend.application.folders.command.trashDeleteFolder.TrashDeleteFolderOutput;
import com.example.hakaton_janvier2026_backend.application.utils.ICommandHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrashRestoreFolderHandler implements ICommandHandler<Integer, TrashRestoreFolderOutput> {
    private final IFolderRepository folderRepository;
    private final INoteRepository noteRepository; // Ajouté
    private final ModelMapper modelMapper;

    public TrashRestoreFolderHandler(IFolderRepository folderRepository, INoteRepository noteRepository, ModelMapper modelMapper) {
        this.folderRepository = folderRepository;
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional // Crucial pour que toute la cascade réussisse ou échoue ensemble
    public TrashRestoreFolderOutput handle(Integer input) {
        DbFolder folder = folderRepository.findById(input)
                .orElseThrow(ParentFolderNotFoundException::new);

        trashRecursive(folder, null);

        return modelMapper.map(folder, TrashRestoreFolderOutput.class);
    }

    private void trashRecursive(DbFolder folder, LocalDateTime deletedAt) {
        // 1. Marquer le dossier actuel comme supprimé
        folder.deletedAt = deletedAt;
        folderRepository.save(folder);

        // 2. Marquer toutes les notes de ce dossier
        List<DbNote> notes = noteRepository.findAllByFolderId(folder.id);
        for (DbNote note : notes) {
            note.deletedAt = deletedAt;
            noteRepository.save(note);
        }

        // 3. Traiter récursivement les sous-dossiers
        List<DbFolder> subFolders = folderRepository.findAllByParentFolderId(folder.id);
        for (DbFolder subFolder : subFolders) {
            trashRecursive(subFolder, deletedAt);
        }
    }
}
