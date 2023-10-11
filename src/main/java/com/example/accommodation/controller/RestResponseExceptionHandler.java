package com.example.accommodation.controller;

import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.InvalidRequestException;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class RestResponseExceptionHandler {
    /**
     * Custom exception, returns 404 if the requested hotel does not exist.
     */
    @ExceptionHandler(NoSuchHotelFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchHotelFoundException(@NonNull NoSuchHotelFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    /**
     * Custom exception, returns 405 if when booking a hotel, the availability is 0.
     */
    @ExceptionHandler(AvailabilityIsZeroException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleAvailabilityIsZeroException(@NonNull AvailabilityIsZeroException exception) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(exception.getMessage());
    }

    /**
     * Custom exception, returns 400 if POST or PUT request body contains invalid values.
     */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleInvalidRequestException(@NonNull InvalidRequestException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getErrorsMap());
    }
}
