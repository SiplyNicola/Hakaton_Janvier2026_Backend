package com.example.hakaton_janvier2026_backend.application.folders.command;

import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderHandler;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderOutput;
// Imports pour le delete
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderHandler;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderOutput;
import org.springframework.stereotype.Service;

@Service
public class FolderCommandProcessor {

    private final CreateFolderHandler createFolderHandler;
    private final DeleteFolderHandler deleteFolderHandler; // <--- Nouveau

    public FolderCommandProcessor(CreateFolderHandler createFolderHandler,
                                  DeleteFolderHandler deleteFolderHandler) {
        this.createFolderHandler = createFolderHandler;
        this.deleteFolderHandler = deleteFolderHandler; // <--- Injection
    }

    // CREATE
    public CreateFolderOutput process(CreateFolderInput input) {
        return createFolderHandler.handle(input);
    }

    // DELETE (Nouvelle méthode)
    public DeleteFolderOutput process(DeleteFolderInput input) {
        return deleteFolderHandler.handle(input);
    }
}