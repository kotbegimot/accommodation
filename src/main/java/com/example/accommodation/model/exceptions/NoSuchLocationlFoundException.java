package com.example.accommodation.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchLocationlFoundException extends RuntimeException {
    public NoSuchLocationlFoundException(int locationId) {
        super("Location ID is not found: " + locationId);
    }
}
