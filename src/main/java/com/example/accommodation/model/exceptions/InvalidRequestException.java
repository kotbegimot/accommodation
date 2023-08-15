package com.example.accommodation.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    private final List<String> errors;

    public InvalidRequestException(List<String> cause) {
        super("Invalid request: " + cause.toString());
        errors = cause;
    }

    public Map<String, List<String>> getErrorsMap() {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
