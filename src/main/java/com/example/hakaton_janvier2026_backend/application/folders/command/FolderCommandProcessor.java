package com.example.hakaton_janvier2026_backend.application.folders.command;

import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderHandler;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderOutput;
import org.springframework.stereotype.Service;

@Service
public class FolderCommandProcessor {

    private final CreateFolderHandler createFolderHandler;

    // Vous ajouterez les autres handlers ici (Delete, Rename, Move...)
    public FolderCommandProcessor(CreateFolderHandler createFolderHandler) {
        this.createFolderHandler = createFolderHandler;
    }

    public CreateFolderOutput process(CreateFolderInput input) {
        return createFolderHandler.handle(input);
    }
}