package com.workshop.taskapi.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard error response structure for API errors.
 */
public class ErrorResponse {

    private String error;
    private String message;
    private List<String> details;
    private LocalDateTime timestamp;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
        this.details = new ArrayList<>();
    }

    public ErrorResponse(String error, String message) {
        this();
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(String error, String message, List<String> details) {
        this(error, message);
        this.details = details;
    }

    // Getters and Setters

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
