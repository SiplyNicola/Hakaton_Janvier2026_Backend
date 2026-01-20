package com.example.hakaton_janvier2026_backend.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("The password you entered is incorrect !");
    }
}
