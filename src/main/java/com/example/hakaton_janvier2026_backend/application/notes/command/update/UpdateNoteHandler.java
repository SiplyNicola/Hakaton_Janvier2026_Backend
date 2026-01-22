
package com.example.hakaton_janvier2026_backend.application.notes.command.update;

import com.example.hakaton_janvier2026_backend.application.exceptions.NoteNotFoundException;
import com.example.hakaton_janvier2026_backend.application.exceptions.ParentFolderNotFoundException;
import com.example.hakaton_janvier2026_backend.application.utils.ICommandHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class UpdateNoteHandler implements ICommandHandler<UpdateNoteInput, UpdateNoteOutput> {

    private final INoteRepository noteRepository;
    private final IFolderRepository folderRepository;
    private final ModelMapper modelMapper;

    public UpdateNoteHandler(INoteRepository noteRepository, IFolderRepository folderRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.folderRepository = folderRepository;
        this.modelMapper = modelMapper;
    }

    public UpdateNoteOutput handle(UpdateNoteInput input) {
        // Retrieve the note from the database
        DbNote dbNote = noteRepository.findById(input.id)
                .orElseThrow(() -> new NoteNotFoundException());

        // Update the content
        dbNote.title = input.title;
        dbNote.content_markdown = input.content_markdown;

        // Real-time metadata computation (Zombie threshold)
        String content = (input.content_markdown != null) ? input.content_markdown : "";

        dbNote.updated_at = LocalDateTime.now();

        if (input.folder_id != null && input.folder_id > 0) {
            dbNote.folder = folderRepository.findById(input.folder_id)
                    .orElseThrow(() -> new ParentFolderNotFoundException());
        } else if (input.folder_id == null || input.folder_id == 0) {
            // Optional: do nothing to keep the previous folder,
            // or dbNote.folder = null; if you want to move the note to the root.
        }

        DbNote saved = noteRepository.save(dbNote);

        // Map to output
        return modelMapper.map(saved, UpdateNoteOutput.class);
    }
}
