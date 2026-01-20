package com.example.hakaton_janvier2026_backend.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ParentFolderNotFoundException extends RuntimeException {
    public ParentFolderNotFoundException() {
        super("Parent folder is not found !");
    }
}
