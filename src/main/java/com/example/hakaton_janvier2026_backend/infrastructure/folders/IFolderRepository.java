package com.example.hakaton_janvier2026_backend.infrastructure.folders;

import com.example.hakaton_janvier2026_backend.domain.Folder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IFolderRepository extends CrudRepository<DbFolder,Integer> {
    List<DbFolder> findAllByOwner_Id(int ownerId);
    void deleteByDeletedAtBefore(LocalDateTime date);

    // Trouve tous les sous-dossiers d'un dossier parent
    List<DbFolder> findAllByParentFolderId(int parentId);

}
