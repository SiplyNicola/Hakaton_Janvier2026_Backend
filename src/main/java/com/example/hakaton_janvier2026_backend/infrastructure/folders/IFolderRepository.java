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

    // Find all folders and their children
    List<DbFolder> findAllByParentFolderId(int parentId);

    List<DbFolder> findAllByParentFolderIdAndDeletedAtIsNull(int parentId);

}
