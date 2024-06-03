package com.api.todolist.handler;

import com.api.todolist.handler.exceptions.BadRequestExceptionDetails;
import com.api.todolist.handler.exceptions.ResourceNotFoundException;
import com.api.todolist.handler.exceptions.ResourceNotFoundExceptionDetails;
import com.api.todolist.handler.exceptions.ValidationExceptionDetails;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResourceNotFoundExceptionDetails> handleNotFoundException(ResourceNotFoundException exception) {
        ResourceNotFoundExceptionDetails details = new ResourceNotFoundExceptionDetails(
                "Not Found Exception: Resource not found.",
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                exception.getClass().getName(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BadRequestExceptionDetails> handleIllegalArgumentException(IllegalArgumentException exception) {
        BadRequestExceptionDetails details = new BadRequestExceptionDetails(
                "Bad Request Exception: Invalid argument.",
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                exception.getClass().getName(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationExceptionDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));
        String fieldsMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ValidationExceptionDetails details = new ValidationExceptionDetails(
                "Bad Request Exception: Invalid fields.",
                HttpStatus.BAD_REQUEST.value(),
                "Invalid fields in the request body.",
                exception.getClass().getName(),
                LocalDateTime.now(),
                fields,
                fieldsMessages
        );

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}