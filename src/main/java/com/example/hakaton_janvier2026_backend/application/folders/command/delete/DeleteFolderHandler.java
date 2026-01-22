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
        // Get the parent folder
        DbFolder folder = folderRepository.findById(input.getFolderId())
                .orElseThrow(ParentFolderNotFoundException::new);

        // Recursive delete
        deleteRecursive(folder);

        return DeleteFolderOutput.builder()
                .deletedFolderId(input.getFolderId())
                .success(true)
                .build();
    }

    private void deleteRecursive(DbFolder folder) {
        // Delete all the notes attached to this folder
        List<DbNote> notes = noteRepository.findAllByFolderId(folder.id);
        for (DbNote note : notes) {
            noteRepository.delete(note);
        }

        // Retrieve all subfolders and delete them recursively
        List<DbFolder> subFolders = folderRepository.findAllByParentFolderId(folder.id);
        for (DbFolder subFolder : subFolders) {
            deleteRecursive(subFolder);
        }

        // Delete the main folder
        folderRepository.delete(folder);
    }
}