package com.example.hakaton_janvier2026_backend.controller.notes;

import com.example.hakaton_janvier2026_backend.application.notes.query.NoteQueryProcessor;
import com.example.hakaton_janvier2026_backend.application.notes.query.getById.GetByIdOutput;
import com.example.hakaton_janvier2026_backend.application.notes.query.getSideBar.GetSideBarInput;
import com.example.hakaton_janvier2026_backend.application.notes.query.getSideBar.GetSideBarOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Return all folders and notes using the id of user")
    @ApiResponse(responseCode = "200")
    @GetMapping("/sidebar/{userId}")
    public ResponseEntity<GetSideBarOutput> getSidebar(@PathVariable int userId) {
        GetSideBarInput input = new GetSideBarInput();
        input.userId = userId;

        GetSideBarOutput output = this.noteQueryProcessor.getSideBarHandler.handle(input);


        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Return the notes data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetByIdOutput> getNoteById(@PathVariable int id) {
        GetByIdOutput output = noteQueryProcessor.getByIdHandler.handle(id);
        return ResponseEntity.ok(output);
    }
}
