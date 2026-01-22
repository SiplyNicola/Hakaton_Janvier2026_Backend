package com.example.hakaton_janvier2026_backend.infrastructure.notes;

import com.example.hakaton_janvier2026_backend.domain.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface INoteRepository extends CrudRepository<DbNote,Integer> {
    List<DbNote> findAllByOwnerId(int ownerId);
    void deleteByDeletedAtBefore(LocalDateTime date);
    // Trouve toutes les notes contenues dans un dossier spécifique
    List<DbNote> findAllByFolderId(int folderId);
    List<DbNote> findAllByFolderIdAndDeletedAtIsNull(int folderId);
}
