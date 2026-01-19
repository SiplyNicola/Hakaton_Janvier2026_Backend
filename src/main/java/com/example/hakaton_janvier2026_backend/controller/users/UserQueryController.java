package com.example.hakaton_janvier2026_backend.controller.users;

import com.example.hakaton_janvier2026_backend.application.users.query.UserQueryProcessor;
import com.example.hakaton_janvier2026_backend.application.users.query.getById.GetByIdUserOutput;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserQueryController {
    private final UserQueryProcessor userQueryProcessor;
    public UserQueryController(UserQueryProcessor userQueryProcessor) {
        this.userQueryProcessor = userQueryProcessor;
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    public ResponseEntity<GetByIdUserOutput> getById(@PathVariable int id) {
        GetByIdUserOutput output = userQueryProcessor.getByIdUserHandler.handle(id);

        return ResponseEntity.ok(output);
    }
}
