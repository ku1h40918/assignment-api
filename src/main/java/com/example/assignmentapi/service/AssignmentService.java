package com.example.assignmentapi.service;

import com.example.assignmentapi.dto.Assignment;
import com.example.assignmentapi.exception.AssignmentArgumentNotValidException;
import com.example.assignmentapi.exception.AssignmentNotFoundException;
import com.example.assignmentapi.repository.AssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

    public Assignment saveAssignment(Assignment assignment) {
        // title が空の場合のバリデーション
        if (assignment.getTitle() == null || assignment.getTitle().isEmpty()) {
            throw new AssignmentArgumentNotValidException("title is empty");
        }

        // content が空の場合のバリデーション
        if (assignment.getContent() == null || assignment.getContent().isEmpty()) {
            throw new AssignmentArgumentNotValidException("content is empty");
        }

        // 締切が指定されていない場合のバリデーション
        if (assignment.getDueDate() == null) {
            throw new AssignmentArgumentNotValidException("dueDate is empty");
        }

        assignmentRepository.insertAssignment(assignment);
        return assignmentRepository.findById(assignment.getId());
    }

    public Assignment findById(long id) {
        Assignment assignment = assignmentRepository.findById(id);

        if (assignment == null) {
            throw new AssignmentNotFoundException(id);
        }

        return assignment;
    }

    public boolean delete(long id) {
        if (!assignmentRepository.delete(id)) {
            throw new AssignmentNotFoundException(id);
        }
        return true;
    }

    @Transactional
    public Assignment update(Assignment assignment, long id) {
        // title が空の場合のバリデーション
        if (assignment.getTitle() == null || assignment.getTitle().isEmpty()) {
            throw new AssignmentArgumentNotValidException("title is empty");
        }

        // content が空の場合のバリデーション
        if (assignment.getContent() == null || assignment.getContent().isEmpty()) {
            throw new AssignmentArgumentNotValidException("content is empty");
        }

        // 締切が指定されていない場合のバリデーション
        if (assignment.getDueDate() == null) {
            throw new AssignmentArgumentNotValidException("dueDate is empty");
        }

        // 指定されたIDが存在しない場合のバリデーション
        Assignment targetAssignment = assignmentRepository.findById(id);
        if (targetAssignment == null) {
            throw new AssignmentNotFoundException(id);
        }

        assignment.setId(id);
        assignment.setCreatedAt(targetAssignment.getCreatedAt());
        return assignmentRepository.update(assignment);
    }
}