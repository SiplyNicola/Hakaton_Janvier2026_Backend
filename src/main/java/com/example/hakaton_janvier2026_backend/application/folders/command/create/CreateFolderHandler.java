package com.example.hakaton_janvier2026_backend.application.folders.command.create;

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

    @Transactional
    public CreateFolderOutput handle(CreateFolderInput input) {
        // 1. Validation / Récupération Owner
        DbUser owner = userRepository.findById(input.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + input.getOwnerId()));

        // 2. Récupération Parent (si existe)
        DbFolder parent = null;
        if (input.getParentId() != null && input.getParentId() != 0) {
            parent = folderRepository.findById(input.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
        }

        // 3. Création Entité DB
        DbFolder dbFolder = new DbFolder();
        if (input.getName() != null && !input.getName().isEmpty()) {
            dbFolder.name = input.getName();
        } else {
            dbFolder.name = "Dossier sans titre";
        }
        dbFolder.owner = owner;
        dbFolder.parentFolder = parent;
        dbFolder.created_at = LocalDateTime.now(); // On force la date pour le retour immédiat

        // 4. Sauvegarde
        DbFolder savedFolder = folderRepository.save(dbFolder);

        // 5. Mapping vers Output
        return CreateFolderOutput.builder()
                .id(savedFolder.id)
                .name(savedFolder.name)
                .createdAt(savedFolder.created_at)
                .ownerId(savedFolder.owner.id)
                .parentId(savedFolder.parentFolder != null ? savedFolder.parentFolder.id : null)
                .build();
    }
}