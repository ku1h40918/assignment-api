package com.example.assignmentapi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Assignment {
    private long id;
    private String title;
    private String content;
    private LocalDate dueDate;
    private boolean completed;
    private LocalDateTime createdAt;
}