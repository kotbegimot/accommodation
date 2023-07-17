package com.example.accommodation.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class AvailabilityIsZeroException extends RuntimeException {
    public AvailabilityIsZeroException(int hotel_id) {
        super("Booking is not allowed: availability of hotel with id: " +  hotel_id + " is 0");
    }
}
