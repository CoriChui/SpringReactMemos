package com.kaonstudio.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleUserMethodFieldErrors(MethodArgumentNotValidException ex, WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String response;
        if (!fieldErrors.isEmpty()) {
            FieldError fieldError = fieldErrors.get(0);
            response = fieldError.getField() + " " + fieldError.getDefaultMessage();
        } else {
            response = Objects.requireNonNull(ex.getGlobalError()).getDefaultMessage();
        }
        ApiException ApiException = new ApiException(
                response,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(ApiException, HttpStatus.BAD_REQUEST);
    }

}
