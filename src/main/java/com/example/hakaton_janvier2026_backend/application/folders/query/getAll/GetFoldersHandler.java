
package com.example.hakaton_janvier2026_backend.application.folders.query.getAll;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetFoldersHandler {

    private final IFolderRepository folderRepository;

    public GetFoldersHandler(IFolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public List<GetFoldersOutput> handle(GetFoldersInput input) {

        // Retrieve all folders belonging to the user
        List<DbFolder> allFolders = folderRepository.findAllByOwner_Id(input.getOwnerId());

        // Prepare a map to easily access folders by their ID
        Map<Integer, GetFoldersOutput> folderMap = new HashMap<>();

        // Create folder DTOs without linking them yet
        for (DbFolder dbFolder : allFolders) {
            if (dbFolder.deletedAt == null) {
                GetFoldersOutput dto = GetFoldersOutput.builder()
                        .id(dbFolder.id)
                        .name(dbFolder.name)
                        .parentId(dbFolder.parentFolder != null
                                ? dbFolder.parentFolder.id
                                : null)
                        .children(new ArrayList<>()) // Important: initialize an empty children list
                        .build();
                folderMap.put(dbFolder.id, dto);
            }
        }

        List<GetFoldersOutput> rootFolders = new ArrayList<>();

        for (GetFoldersOutput dto : folderMap.values()) {
            if (dto.getParentId() == null) {
                // Root folder → add to the main list
                rootFolders.add(dto);
            } else {
                // Child folder → add to its parent's children list
                GetFoldersOutput parent = folderMap.get(dto.getParentId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }

        return rootFolders;
    }
}