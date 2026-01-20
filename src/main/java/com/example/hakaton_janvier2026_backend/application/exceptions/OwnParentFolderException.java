package com.example.hakaton_janvier2026_backend.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OwnParentFolderException extends RuntimeException {
    public OwnParentFolderException() {
        super("A folder cannot be its own parent.");
    }
}
