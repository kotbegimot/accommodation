package com.example.accommodation.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class AvailabilityIsZeroException extends RuntimeException {
    public AvailabilityIsZeroException(int hotelId) {
        super("Booking is not allowed: availability of hotel with id: " +  hotelId + " is 0");
    }
}
