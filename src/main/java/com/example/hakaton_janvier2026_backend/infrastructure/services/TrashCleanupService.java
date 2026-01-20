package com.example.hakaton_janvier2026_backend.infrastructure.services;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrashCleanupService {

    private final INoteRepository noteRepository;
    private final IFolderRepository folderRepository;

    public TrashCleanupService(INoteRepository noteRepository, IFolderRepository folderRepository) {
        this.noteRepository = noteRepository;
        this.folderRepository = folderRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // S'exécute chaque nuit à minuit
    @Transactional
    public void cleanOldSpells() {
        LocalDateTime limit = LocalDateTime.now().minusDays(30);
        noteRepository.deleteByDeletedAtBefore(limit);
        folderRepository.deleteByDeletedAtBefore(limit);
    }
}
