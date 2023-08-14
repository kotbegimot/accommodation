package com.example.accommodation.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchHotelFoundException extends RuntimeException {
    public NoSuchHotelFoundException(int hotelId) {
        super("Hotel ID is not found: " + hotelId);
    }
}
