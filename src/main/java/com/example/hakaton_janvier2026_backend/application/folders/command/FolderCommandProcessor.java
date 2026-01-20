package com.example.hakaton_janvier2026_backend.application.folders.command;

import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderHandler;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.create.CreateFolderOutput;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderHandler;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.delete.DeleteFolderOutput;
// Imports Update
import com.example.hakaton_janvier2026_backend.application.folders.command.trashDeleteFolder.TrashDeleteFolderHandler;
import com.example.hakaton_janvier2026_backend.application.folders.command.update.UpdateFolderHandler;
import com.example.hakaton_janvier2026_backend.application.folders.command.update.UpdateFolderInput;
import com.example.hakaton_janvier2026_backend.application.folders.command.update.UpdateFolderOutput;
import org.springframework.stereotype.Service;

@Service
public class FolderCommandProcessor {

    private final CreateFolderHandler createFolderHandler;
    private final DeleteFolderHandler deleteFolderHandler;
    private final UpdateFolderHandler updateFolderHandler;
    public final TrashDeleteFolderHandler trashDeleteFolderHandler;

    public FolderCommandProcessor(CreateFolderHandler createFolderHandler,
                                  DeleteFolderHandler deleteFolderHandler,
                                  UpdateFolderHandler updateFolderHandler, TrashDeleteFolderHandler trashDeleteFolderHandler) {
        this.createFolderHandler = createFolderHandler;
        this.deleteFolderHandler = deleteFolderHandler;
        this.updateFolderHandler = updateFolderHandler;
        this.trashDeleteFolderHandler = trashDeleteFolderHandler;
    }

    public CreateFolderOutput process(CreateFolderInput input) {
        return createFolderHandler.handle(input);
    }

    public DeleteFolderOutput process(DeleteFolderInput input) {
        return deleteFolderHandler.handle(input);
    }

    public UpdateFolderOutput process(UpdateFolderInput input) {
        return updateFolderHandler.handle(input);
    }
}