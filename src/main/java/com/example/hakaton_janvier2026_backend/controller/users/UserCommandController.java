package com.example.hakaton_janvier2026_backend.controller.users;

import com.example.hakaton_janvier2026_backend.application.users.command.UserCommandProcessor;
import com.example.hakaton_janvier2026_backend.application.users.command.create.CreateUserInput;
import com.example.hakaton_janvier2026_backend.application.users.command.create.CreateUserOutput;
import com.example.hakaton_janvier2026_backend.application.users.command.login.LoginUserInput;
import com.example.hakaton_janvier2026_backend.application.users.command.login.LoginUserOutput;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api")
public class UserCommandController {
    private final UserCommandProcessor userCommandProcessor;

    public UserCommandController(UserCommandProcessor userCommandProcessor) {
        this.userCommandProcessor = userCommandProcessor;
    }

    // Create an user
    @Operation(summary = "Create an user")
    @PostMapping("/users")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created !", content = @Content),
            @ApiResponse(
                    responseCode = "409",
                    description = "Username already exists in database !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            ),
    })
    public ResponseEntity<CreateUserOutput> createUser(@RequestBody CreateUserInput createUserInput) {
        CreateUserOutput output = userCommandProcessor.createUserHandler.handle(createUserInput);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(output.id)
                .toUri();

        return ResponseEntity.created(location).body(output);
    }

    // Authenticates a user using a username and password.
    @Operation(summary = "Log in with an username and password, return the id and the username from the users existing in the database")
    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User is logged in !", content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not found !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "The password entered is invalid !",
                    content = @Content(schema = @Schema(implementation = org.springframework.http.ProblemDetail.class))
            )
    })
    public ResponseEntity<LoginUserOutput> login(@RequestBody LoginUserInput loginUserInput) {
        LoginUserOutput output = userCommandProcessor.loginUserHandler.handle(loginUserInput);

        return ResponseEntity.ok(output);
    }
}
