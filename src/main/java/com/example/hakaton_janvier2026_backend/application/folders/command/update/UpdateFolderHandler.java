
package com.example.hakaton_janvier2026_backend.application.folders.command.update;

import com.example.hakaton_janvier2026_backend.application.exceptions.OwnParentFolderException;
import com.example.hakaton_janvier2026_backend.application.exceptions.ParentFolderNotFoundException;
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

    public UpdateFolderOutput handle(UpdateFolderInput input) {

        // Retrieve the existing folder
        DbFolder folderToUpdate = folderRepository.findById(input.getId())
                .orElseThrow(ParentFolderNotFoundException::new);

        // Update the folder name
        if (input.getName() != null && !input.getName().isBlank()) {
            folderToUpdate.name = input.getName();
        }

        // Update the parent folder (move operation)
        if (input.getParentId() != null && input.getParentId() == input.getId()) {
            throw new OwnParentFolderException();
        }

        if (input.getParentId() == null || input.getParentId() == 0) {
            // Move the folder to the root level
            folderToUpdate.parentFolder = null;
        } else {
            // Move the folder to another parent folder
            DbFolder newParent = folderRepository.findById(input.getParentId()).orElseThrow(ParentFolderNotFoundException::new);
            folderToUpdate.parentFolder = newParent;
        }

        DbFolder updatedFolder = folderRepository.save(folderToUpdate);

        // Build and return the output
        return UpdateFolderOutput.builder()
                .id(updatedFolder.id)
                .name(updatedFolder.name)
                .ownerId(updatedFolder.owner.id)
                .parentId(updatedFolder.parentFolder != null ? updatedFolder.parentFolder.id : null)
                .build();
    }
}
