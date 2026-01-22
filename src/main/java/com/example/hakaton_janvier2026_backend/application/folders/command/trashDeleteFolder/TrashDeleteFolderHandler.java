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
    private final INoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public TrashDeleteFolderHandler(IFolderRepository folderRepository, INoteRepository noteRepository, ModelMapper modelMapper) {
        this.folderRepository = folderRepository;
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TrashDeleteFolderOutput handle(Integer input) {

        // Retrieve the folder to delete or throw an exception if it does not exist
        DbFolder folder = folderRepository.findById(input)
                .orElseThrow(ParentFolderNotFoundException::new);

        LocalDateTime now = LocalDateTime.now();

        // Start recursive trash deletion for the folder and all its contents
        trashRecursive(folder, now);

        return modelMapper.map(folder, TrashDeleteFolderOutput.class);
    }

    private void trashRecursive(DbFolder folder, LocalDateTime deletedAt) {

        // Mark the current folder as deleted (move into the bin)
        folder.deletedAt = deletedAt;
        folderRepository.save(folder);

        // Mark all notes contained in this folder as deleted
        List<DbNote> notes = noteRepository.findAllByFolderId(folder.id);
        for (DbNote note : notes) {
            note.deletedAt = deletedAt;
            noteRepository.save(note);
        }

        // Recursively process and trash all subfolders
        List<DbFolder> subFolders = folderRepository.findAllByParentFolderId(folder.id);
        for (DbFolder subFolder : subFolders) {
            trashRecursive(subFolder, deletedAt);
        }
    }
}
