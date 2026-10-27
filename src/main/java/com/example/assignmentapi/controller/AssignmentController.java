package com.example.assignmentapi.controller;

import com.example.assignmentapi.dto.Assignment;
import com.example.assignmentapi.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> findAllAssignments() {
        List<Assignment> assignments = assignmentService.findAll();

        return ResponseEntity.ok(assignments);
    }

    @PostMapping
    public ResponseEntity<Assignment> saveAssignment(
            @RequestBody Assignment assignment,
            UriComponentsBuilder uriBuilder) {

        Assignment createdAssignment =
                assignmentService.saveAssignment(assignment);

        URI location = uriBuilder.path("/api/assignments/{id}")
                .buildAndExpand(createdAssignment.getId()).toUri();

        return ResponseEntity.created(location).body(createdAssignment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> findById(@PathVariable long id) {
        Assignment assignment = assignmentService.findById(id);

        return ResponseEntity.ok(assignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        assignmentService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> update(
            @PathVariable long id,
            @RequestBody Assignment assignment) {

        assignmentService.update(assignment, id);

        return ResponseEntity.ok(assignment);
    }
}