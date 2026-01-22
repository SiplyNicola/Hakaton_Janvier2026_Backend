package com.example.hakaton_janvier2026_backend.application.folders.command.create;

import com.example.hakaton_janvier2026_backend.application.exceptions.ParentFolderNotFoundException;
import com.example.hakaton_janvier2026_backend.application.exceptions.UserNotFoundException;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.users.DbUser;
import com.example.hakaton_janvier2026_backend.infrastructure.users.IUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class CreateFolderHandler {

    private final IFolderRepository folderRepository;
    private final IUserRepository userRepository;

    public CreateFolderHandler(IFolderRepository folderRepository, IUserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }

    public CreateFolderOutput handle(CreateFolderInput input) {
        // Get the owner
        DbUser owner = userRepository.findById(input.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException());

        // Get the parent if he's existing
        DbFolder parent = null;
        if (input.getParentId() != null && input.getParentId() != 0) {
            parent = folderRepository.findById(input.getParentId())
                    .orElseThrow(() -> new ParentFolderNotFoundException());
        }

        DbFolder dbFolder = new DbFolder();
        if (input.getName() != null && !input.getName().isEmpty()) {
            dbFolder.name = input.getName();
        } else {
            dbFolder.name = "Untitled Folder";
        }
        dbFolder.owner = owner;
        dbFolder.parentFolder = parent;
        dbFolder.created_at = LocalDateTime.now(); // On force la date pour le retour immédiat

        DbFolder savedFolder = folderRepository.save(dbFolder);

        return CreateFolderOutput.builder()
                .id(savedFolder.id)
                .name(savedFolder.name)
                .createdAt(savedFolder.created_at)
                .ownerId(savedFolder.owner.id)
                .parentId(savedFolder.parentFolder != null ? savedFolder.parentFolder.id : null)
                .build();
    }
}