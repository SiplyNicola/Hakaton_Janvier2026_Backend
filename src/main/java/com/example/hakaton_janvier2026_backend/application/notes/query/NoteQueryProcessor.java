package com.example.hakaton_janvier2026_backend.application.notes.query;

import com.example.hakaton_janvier2026_backend.application.notes.query.getSideBar.GetSideBarHandler;
import org.springframework.stereotype.Service;

@Service
public class NoteQueryProcessor {

    public final GetSideBarHandler getSideBarHandler;

    public NoteQueryProcessor(GetSideBarHandler getSideBarHandler) {
        this.getSideBarHandler = getSideBarHandler;
    }
}
