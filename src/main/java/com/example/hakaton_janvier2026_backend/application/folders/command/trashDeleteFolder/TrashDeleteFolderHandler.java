package com.example.hakaton_janvier2026_backend.application.folders.command.trashDeleteFolder;

import com.example.hakaton_janvier2026_backend.application.exceptions.ParentFolderNotFoundException;
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
public class TrashDeleteFolderHandler implements ICommandHandler<Integer, TrashDeleteFolderOutput> {

    private final IFolderRepository folderRepository;
    private final INoteRepository noteRepository; // Ajouté
    private final ModelMapper modelMapper;

    public TrashDeleteFolderHandler(IFolderRepository folderRepository,
                                    INoteRepository noteRepository,
                                    ModelMapper modelMapper) {
        this.folderRepository = folderRepository;
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional // Crucial pour que toute la cascade réussisse ou échoue ensemble
    public TrashDeleteFolderOutput handle(Integer input) {
        DbFolder folder = folderRepository.findById(input)
                .orElseThrow(ParentFolderNotFoundException::new);

        LocalDateTime now = LocalDateTime.now();

        // Lancer la récursion pour tout le contenu
        trashRecursive(folder, now);

        return modelMapper.map(folder, TrashDeleteFolderOutput.class);
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