package com.example.hakaton_janvier2026_backend.application.folders.query.getall;

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

    @Transactional(readOnly = true) // Optimisation pour la lecture
    public List<FolderTreeOutput> handle(GetFoldersInput input) {
        // 1. Récupérer TOUS les dossiers de l'utilisateur (Liste plate)
        List<DbFolder> allFolders = folderRepository.findAllByOwner_Id(input.getOwnerId());

        // 2. Préparer une Map pour retrouver facilement les dossiers par ID
        Map<Integer, FolderTreeOutput> folderMap = new HashMap<>();

        // 3. Première passe : Créer les DTOs sans les lier
        for (DbFolder dbFolder : allFolders) {
            FolderTreeOutput dto = FolderTreeOutput.builder()
                    .id(dbFolder.id)
                    .name(dbFolder.name)
                    .parentId(dbFolder.parentFolder != null ? dbFolder.parentFolder.id : null)
                    .children(new ArrayList<>()) // Important : initialiser la liste vide
                    .build();
            folderMap.put(dbFolder.id, dto);
        }

        // 4. Deuxième passe : Construire l'arbre
        List<FolderTreeOutput> rootFolders = new ArrayList<>();

        for (FolderTreeOutput dto : folderMap.values()) {
            if (dto.getParentId() == null) {
                // C'est une racine -> On l'ajoute à la liste principale
                rootFolders.add(dto);
            } else {
                // C'est un enfant -> On l'ajoute à la liste de son parent
                FolderTreeOutput parent = folderMap.get(dto.getParentId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }

        return rootFolders;
    }
}