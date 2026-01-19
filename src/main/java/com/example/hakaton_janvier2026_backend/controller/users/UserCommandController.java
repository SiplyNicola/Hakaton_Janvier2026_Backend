package com.example.hakaton_janvier2026_backend.controller.users;

import com.example.hakaton_janvier2026_backend.application.users.command.UserCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.users.command.create.UserCreateHandle;
import com.example.hakaton_janvier2026_backend.application.users.command.create.UserCreateInput;
import com.example.hakaton_janvier2026_backend.application.users.command.create.UserCreateOutput;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserCommandController {
    private final UserCommandProcessor userCommandProcessor;

    public UserCommandController(UserCommandProcessor userCommandProcessor) {
        this.userCommandProcessor = userCommandProcessor;
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created !", content = @Content),
            @ApiResponse(
                    responseCode = "409",
                    description = "Username already exists in database !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            ),
    })
    public ResponseEntity<UserCreateOutput> createUser(@RequestBody UserCreateInput userCreateInput) {
        UserCreateOutput output = userCommandProcessor.userCreateHandle.handle(userCreateInput);

        // Construction de l'URI de la nouvelle ressource (bonne pratique multi-tiers)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(output.id)
                .toUri();

        return ResponseEntity.created(location).body(output);
    }
}
