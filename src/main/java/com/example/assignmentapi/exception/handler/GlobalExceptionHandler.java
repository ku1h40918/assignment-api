package com.example.assignmentapi.exception.handler;

import com.example.assignmentapi.exception.AssignmentArgumentNotValidException;
import com.example.assignmentapi.exception.AssignmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AssignmentNotFoundException.class)
    public ResponseEntity<String> handleAssignmentNotFoundException(
            AssignmentNotFoundException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssignmentArgumentNotValidException.class)
    public ResponseEntity<String> handleAssignmentArgumentNotValidException(
            AssignmentArgumentNotValidException e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }
}