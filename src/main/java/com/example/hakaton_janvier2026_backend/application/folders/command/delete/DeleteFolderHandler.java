package com.example.hakaton_janvier2026_backend.application.folders.command.delete;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteFolderHandler {

    private final IFolderRepository folderRepository;

    public DeleteFolderHandler(IFolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @Transactional
    public DeleteFolderOutput handle(DeleteFolderInput input) {
        // 1. Vérifier si le dossier existe
        if (!folderRepository.existsById(input.getFolderId())) {
            throw new RuntimeException("Folder not found with ID: " + input.getFolderId());
        }

        // 2. Supprimer (La DB gère la cascade pour les enfants)
        folderRepository.deleteById(input.getFolderId());

        // 3. Retourner la confirmation
        return DeleteFolderOutput.builder()
                .deletedFolderId(input.getFolderId())
                .success(true)
                .build();
    }
}