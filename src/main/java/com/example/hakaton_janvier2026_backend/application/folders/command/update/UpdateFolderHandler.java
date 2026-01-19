package com.example.hakaton_janvier2026_backend.application.folders.command.update;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateFolderHandler {

    private final IFolderRepository folderRepository;

    public UpdateFolderHandler(IFolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @Transactional
    public UpdateFolderOutput handle(UpdateFolderInput input) {
        // 1. Récupérer le dossier existant
        DbFolder folderToUpdate = folderRepository.findById(input.getId())
                .orElseThrow(() -> new RuntimeException("Folder not found with ID: " + input.getId()));

        // 2. Mise à jour du NOM
        if (input.getName() != null && !input.getName().isBlank()) {
            folderToUpdate.name = input.getName();
        }

        // 3. Mise à jour du PARENT (Déplacement)
        // On vérifie d'abord qu'on essaie pas de devenir son propre parent (Boucle infinie)
        if (input.getParentId() != null && input.getParentId() == input.getId()) {
            throw new RuntimeException("A folder cannot be its own parent.");
        }

        if (input.getParentId() == null || input.getParentId() == 0) {
            // Déplacement vers la racine
            folderToUpdate.parentFolder = null;
        } else {
            // Déplacement vers un autre dossier
            DbFolder newParent = folderRepository.findById(input.getParentId())
                    .orElseThrow(() -> new RuntimeException("New parent folder not found"));
            folderToUpdate.parentFolder = newParent;
        }

        // 4. Sauvegarde
        DbFolder updatedFolder = folderRepository.save(folderToUpdate);

        // 5. Output
        return UpdateFolderOutput.builder()
                .id(updatedFolder.id)
                .name(updatedFolder.name)
                .ownerId(updatedFolder.owner.id)
                .parentId(updatedFolder.parentFolder != null ? updatedFolder.parentFolder.id : null)
                .build();
    }
}