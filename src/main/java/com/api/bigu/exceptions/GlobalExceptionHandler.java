package com.api.bigu.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), errors);
        return new ResponseEntity<>(err, new HttpHeaders(), status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> handleNotFoundException(UserNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), errors);
        return new ResponseEntity<>(err, new HttpHeaders(), status);
    }

    @ExceptionHandler(JwtException.class)
    public final ResponseEntity<StandardError> handleJwtExceptions(JwtException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(Instant.now(), status.value(), errors);
        return new ResponseEntity<>(err, new HttpHeaders(), status);
    }

}
