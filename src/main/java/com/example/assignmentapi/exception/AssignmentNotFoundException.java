package com.example.assignmentapi.exception;

public class AssignmentNotFoundException extends RuntimeException {
    public AssignmentNotFoundException(long id) {
        super("指定されたIDの課題が見つかりません: " + id);
    }
}