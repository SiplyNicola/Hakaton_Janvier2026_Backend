package com.example.hakaton_janvier2026_backend.application.folders.command.trashRestoreFolder;

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
public class TrashRestoreFolderHandler implements ICommandHandler<Integer, TrashRestoreFolderOutput> {

    private final IFolderRepository folderRepository;
    private final INoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public TrashRestoreFolderHandler(IFolderRepository folderRepository,
                                     INoteRepository noteRepository,
                                     ModelMapper modelMapper) {
        this.folderRepository = folderRepository;
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TrashRestoreFolderOutput handle(Integer input) {

        // Retrieve the folder to restore or throw an exception if it does not exist
        DbFolder folder = folderRepository.findById(input)
                .orElseThrow(ParentFolderNotFoundException::new);

        // Restore the folder and all its contents recursively
        trashRecursive(folder, null);

        return modelMapper.map(folder, TrashRestoreFolderOutput.class);
    }

    private void trashRecursive(DbFolder folder, LocalDateTime deletedAt) {

        // Restore the current folder (clear the deletedAt timestamp)
        folder.deletedAt = deletedAt;
        folderRepository.save(folder);

        // Restore all notes contained in this folder
        List<DbNote> notes = noteRepository.findAllByFolderId(folder.id);
        for (DbNote note : notes) {
            note.deletedAt = deletedAt;
            noteRepository.save(note);
        }

        // Recursively restore all subfolders
        List<DbFolder> subFolders = folderRepository.findAllByParentFolderId(folder.id);
        for (DbFolder subFolder : subFolders) {
            trashRecursive(subFolder, deletedAt);
        }
    }
}
