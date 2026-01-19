package com.example.hakaton_janvier2026_backend.application.folders.query.getAll;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true) // lecture
    public List<FolderTreeOutput> handle(GetFoldersInput input) {
        // Récupérer tous les dossiers de l'utilisateur
        List<DbFolder> allFolders = folderRepository.findAllByOwner_Id(input.getOwnerId());

        // Préparer une Map pour retrouver facilement les dossiers par ID
        Map<Integer, FolderTreeOutput> folderMap = new HashMap<>();

        // Créer les DTOs sans les lier
        for (DbFolder dbFolder : allFolders) {
            FolderTreeOutput dto = FolderTreeOutput.builder()
                    .id(dbFolder.id)
                    .name(dbFolder.name)
                    .parentId(dbFolder.parentFolder != null ? dbFolder.parentFolder.id : null)
                    .children(new ArrayList<>()) // Important : initialiser la liste vide
                    .build();
            folderMap.put(dbFolder.id, dto);
        }

        // Construire l'arbre
        List<FolderTreeOutput> rootFolders = new ArrayList<>();

        for (FolderTreeOutput dto : folderMap.values()) {
            if (dto.getParentId() == null) {
                // racine -> liste principale
                rootFolders.add(dto);
            } else {
                // enfant -> liste de son parent
                FolderTreeOutput parent = folderMap.get(dto.getParentId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }

        return rootFolders;
    }
}