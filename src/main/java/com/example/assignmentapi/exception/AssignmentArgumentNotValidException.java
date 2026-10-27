package com.example.assignmentapi.exception;

public class AssignmentArgumentNotValidException extends RuntimeException {
    public AssignmentArgumentNotValidException(String message) {
        super(message);
    }
}