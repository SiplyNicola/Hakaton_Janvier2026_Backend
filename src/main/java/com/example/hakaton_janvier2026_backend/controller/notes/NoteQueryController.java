package com.example.hakaton_janvier2026_backend.controller.notes;

import com.example.hakaton_janvier2026_backend.application.notes.query.NoteQueryProcessor;
import com.example.hakaton_janvier2026_backend.application.notes.query.getSideBar.GetSideBarInput;
import com.example.hakaton_janvier2026_backend.application.notes.query.getSideBar.GetSideBarOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/note")
public class NoteQueryController {

    private final NoteQueryProcessor noteQueryProcessor;

    public NoteQueryController(NoteQueryProcessor noteQueryProcessor) {
        this.noteQueryProcessor = noteQueryProcessor;
    }

    @Operation(summary = "List all notes and folders")
    @ApiResponse(responseCode = "200")
    @GetMapping("/sidebar/{userId}")
    public ResponseEntity<GetSideBarOutput> getSidebar(@PathVariable int userId) {
        GetSideBarInput input = new GetSideBarInput();
        input.userId = userId;

        GetSideBarOutput output = this.noteQueryProcessor.getSideBarHandler.handle(input);


        return ResponseEntity.ok(output);
    }
}
