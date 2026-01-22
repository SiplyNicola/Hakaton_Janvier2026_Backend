package com.example.hakaton_janvier2026_backend.application.notes.query.getTrash;

import com.example.hakaton_janvier2026_backend.application.exceptions.UserNotFoundException;
import com.example.hakaton_janvier2026_backend.application.notes.query.getSideBar.GetSideBarInput;
import com.example.hakaton_janvier2026_backend.application.notes.query.getSideBar.GetSideBarOutput;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.users.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetTrashHandler {

    private final IFolderRepository folderRepository;
    private final INoteRepository noteRepository;
    private final IUserRepository userRepository;

    public GetTrashHandler(IFolderRepository folderRepository, INoteRepository noteRepository, IUserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }


    public GetTrashOutput handle(GetTrashInput input) {
        if (!userRepository.existsById(input.userId)) throw new UserNotFoundException();
        List<DbFolder> allFolders = folderRepository
                .findAllByOwner_Id(input.userId)
                .stream().filter(f -> f.deletedAt != null)
                .toList();
        List<DbNote> allNotes = noteRepository
                .findAllByOwnerId(input.userId)
                .stream().filter(n -> n.deletedAt != null)
                .toList();

        GetTrashOutput output = new GetTrashOutput();
        Map<Integer, GetTrashOutput.FolderNode> folderMap = new HashMap<>();

        // 1. Initialisation de la map des dossiers
        for (DbFolder dbFolder : allFolders) {
                GetTrashOutput.FolderNode node = new GetTrashOutput.FolderNode();
                node.id = dbFolder.id;
                node.name = dbFolder.name;
                node.deletedAt = dbFolder.deletedAt;
                folderMap.put(node.id, node);
        }

        // 2. Distribution des notes DANS les dossiers uniquement
        for (DbNote dbNote : allNotes) {
            //if (dbNote.deletedAt != null) {
                GetTrashOutput.NoteNode noteNode = new GetTrashOutput.NoteNode();
                noteNode.id = dbNote.id;
                noteNode.title = dbNote.title;
                noteNode.content_markdown = dbNote.content_markdown;
                noteNode.deletedAt = dbNote.deletedAt;

                if (dbNote.folder == null) {
                    output.notes.add(noteNode);
                } else if (folderMap.containsKey(dbNote.folder.id)) {
                    folderMap.get(dbNote.folder.id).notes.add(noteNode);
                } else {
                    output.notes.add(noteNode);
                }
            //}
        }


        for (DbFolder dbFolder : allFolders) {
            //if (dbFolder.deletedAt != null) {
                GetTrashOutput.FolderNode currentNode = folderMap.get(dbFolder.id);
                if (dbFolder.parentFolder == null) {
                    // Dossier racine supprimé
                    output.folders.add(currentNode);
                } else if (folderMap.containsKey(dbFolder.parentFolder.id)) {
                    // Le parent est supprimé aussi → on l'imbrique
                    folderMap.get(dbFolder.parentFolder.id).subFolders.add(currentNode);
                } else {
                    // Le parent n'est PAS supprimé → on le met en racine de la poubelle
                    output.folders.add(currentNode);
                }
            //}
        }

        return output;

    }
}
