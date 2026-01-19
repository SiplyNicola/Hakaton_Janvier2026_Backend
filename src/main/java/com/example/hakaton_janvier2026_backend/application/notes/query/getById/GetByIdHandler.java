package com.example.hakaton_janvier2026_backend.application.notes.query.getById;

import com.example.hakaton_janvier2026_backend.application.exceptions.NoteNotFoundException;
import com.example.hakaton_janvier2026_backend.application.utils.IQueryHandler;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GetByIdHandler implements IQueryHandler<Integer, GetByIdOutput> {
    private final INoteRepository noteRepository;
    private final ModelMapper modelMapper;

    public GetByIdHandler(INoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GetByIdOutput handle(Integer input) {
        return noteRepository.findById(input)
                .map(note -> modelMapper.map(note, GetByIdOutput.class))
                .orElseThrow(() -> new NoteNotFoundException());
    }
}
