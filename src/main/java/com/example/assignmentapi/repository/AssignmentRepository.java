package com.example.assignmentapi.repository;

import com.example.assignmentapi.dto.Assignment;
import com.example.assignmentapi.mapper.AssignmentMapper;
import org.springframework.stereotype.Repository;
import com.example.assignmentapi.exception.AssignmentNotFoundException;
import java.util.List;

@Repository
public class AssignmentRepository {
    private final AssignmentMapper assignmentMapper;

    public AssignmentRepository(AssignmentMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }

    public List<Assignment> findAll() {
        return assignmentMapper.findAll();
    }

    public int insertAssignment(Assignment assignment) {
        return assignmentMapper.insert(assignment);
    }

    public Assignment findById(long id) {
        return assignmentMapper.findById(id);
    }

    public boolean delete(long id) {
        return assignmentMapper.delete(id) == 1;
    }
    public Assignment update(Assignment assignment) {
        if (assignmentMapper.update(assignment) == 0) {
            throw new AssignmentNotFoundException(assignment.getId());
        }
        return assignment;
    }
}