package com.example.accommodation.controller;

import com.example.accommodation.model.ErrorResponseModel;
import com.example.accommodation.model.exceptions.*;
import com.example.accommodation.properties.MainProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class RestResponseExceptionHandler {
    @NonNull private final MainProperties properties;

    /**
     * 400 Custom exception is thrown by validation service, returns BAD_REQUEST status
     * if POST/PUT/PATCH request body contains invalid values.
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponseModel> handleInvalidRequestException(@NonNull InvalidRequestException exception, @NonNull HttpServletRequest request) {
        String timeStamp =
                new SimpleDateFormat(properties.getExceptionDateFormat()).format(new java.util.Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponseModel.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .messages(exception.getErrorsList())
                                .path(request.getRequestURL().toString())
                                .timestamp(timeStamp)
                                .build());
    }

    /**
     * 400 Custom exception is thrown by @Valid annotations, returns BAD_REQUEST status
     * if POST/PUT request body contains invalid values.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseModel> handleValidationExceptions(
            @NonNull MethodArgumentNotValidException ex, @NonNull HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            FieldError err = ((FieldError) error);
                            String errorStr =
                                    "Field error in object '%s' on field '%s': rejected value [%s]; error: %s"
                                            .formatted(
                                                    err.getObjectName(),
                                                    err.getField(),
                                                    ObjectUtils.nullSafeToString((err).getRejectedValue()),
                                                    err.getDefaultMessage());
                            errors.add(errorStr);
                        });
        String timeStamp =
                new SimpleDateFormat(properties.getExceptionDateFormat()).format(new java.util.Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponseModel.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .messages(errors)
                                .path(request.getRequestURL().toString())
                                .timestamp(timeStamp)
                                .build());
    }

    /**
     * 400 Custom exception is thrown by @Validated annotations, returns BAD_REQUEST status  if POST/PUT request fields,
     * variables or parameters contains invalid values.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponseModel> handleOnConstraintValidationException(
            @NonNull ConstraintViolationException ex, @NonNull HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations()
                .forEach(
                        (violation) -> {
                            String errorStr =
                                    "Field error in object '%s': rejected value [%s]; error: %s"
                                            .formatted(
                                                    violation.getPropertyPath().toString(),
                                                    violation.getInvalidValue().toString(),
                                                    violation.getMessage());
                            errors.add(errorStr);
                        });
        String timeStamp =
                new SimpleDateFormat(properties.getExceptionDateFormat()).format(new java.util.Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponseModel.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .messages(errors)
                                .path(request.getRequestURL().toString())
                                .timestamp(timeStamp)
                                .build());

    }

    /**
     * 404 Custom exception, returns NOT_FOUND status if the requested hotel does not exist.
     */
    @ExceptionHandler(NoSuchHotelFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleNoSuchHotelFoundException(@NonNull NoSuchHotelFoundException exception, @NonNull HttpServletRequest request) {
        String timeStamp = new SimpleDateFormat().format(new java.util.Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponseModel.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                                .messages(List.of((exception.getMessage())))
                                .path(request.getRequestURL().toString())
                                .timestamp(timeStamp)
                                .build());
    }

    @ExceptionHandler(NoSuchLocationlFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleNoSuchLocationlFoundException(@NonNull NoSuchLocationlFoundException exception, @NonNull HttpServletRequest request) {
        String timeStamp = new SimpleDateFormat().format(new java.util.Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponseModel.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                                .messages(List.of((exception.getMessage())))
                                .path(request.getRequestURL().toString())
                                .timestamp(timeStamp)
                                .build());
    }

    /**
     * 405 Custom exception, returns METHOD_NOT_ALLOWED status if when booking a hotel, the availability is 0.
     */
    @ExceptionHandler(AvailabilityIsZeroException.class)
    public ResponseEntity<ErrorResponseModel> handleAvailabilityIsZeroException(@NonNull AvailabilityIsZeroException exception, @NonNull HttpServletRequest request) {
        String timeStamp = new SimpleDateFormat().format(new java.util.Date());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(
                        ErrorResponseModel.builder()
                                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                                .error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                                .messages(List.of((exception.getMessage())))
                                .path(request.getRequestURL().toString())
                                .timestamp(timeStamp)
                                .build());
    }

    /**
     * 409 Custom exception, returns CONFLICT status if the created todos already exists.
     */
    @ExceptionHandler(HotelAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseModel> handleHotelAlreadyExistsException(
            @NonNull HotelAlreadyExistsException exception, @NonNull HttpServletRequest request) {
        String timeStamp =
                new SimpleDateFormat(properties.getExceptionDateFormat()).format(new java.util.Date());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponseModel.builder()
                                .status(HttpStatus.CONFLICT.value())
                                .error(HttpStatus.CONFLICT.getReasonPhrase())
                                .messages(List.of((exception.getMessage())))
                                .path(request.getRequestURL().toString())
                                .timestamp(timeStamp)
                                .build());
    }

    /**
     * 500 Custom exception, returns INTERNAL_SERVER_ERROR status if the unchecked exception is caught
     */
    @ExceptionHandler(RuntimeException.class)
    public final @NonNull ResponseEntity<ErrorResponseModel> handleRuntimeExceptions(
            @NonNull RuntimeException exception, @NonNull HttpServletRequest request) {
        String timeStamp =
                new SimpleDateFormat(properties.getExceptionDateFormat()).format(new java.util.Date());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponseModel.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .messages(List.of((exception.getMessage())))
                                .path(request.getRequestURL().toString())
                                .timestamp(timeStamp)
                                .build());
    }
}
