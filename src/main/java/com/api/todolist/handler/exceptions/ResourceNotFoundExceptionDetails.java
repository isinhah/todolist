package com.api.todolist.handler.exceptions;

import java.time.LocalDateTime;

public class ResourceNotFoundExceptionDetails extends ExceptionDetails {
    public ResourceNotFoundExceptionDetails(String title, int status, String details, String developerMessage, LocalDateTime timestamp) {
        super(title, status, details, developerMessage, timestamp);
    }
}
