package com.example.accommodation.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class HotelAlreadyExistsException extends RuntimeException {
    public HotelAlreadyExistsException(String name) {
        super("Hotel with this name already exists: " + name);
    }
}