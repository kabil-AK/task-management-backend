package com.example.taskmanagement.controller;

import java.util.List;
import com.example.taskmanagement.dto.TaskResponse;


import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE
   
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {
    	System.out.println("CREATE TASK CONTROLLER REACHED");
    	System.out.println("USER = " + authentication.getName());
        String email = authentication.getName();

        TaskResponse response =
                taskService.createTask(request, email);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }
    // GET ALL
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                taskService.getAllTasks(email)
        );
    }
    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                taskService.getTaskById(id, email)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                taskService.updateTask(id, request, email)
        );
    }
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        taskService.deleteTask(id, email);

        return ResponseEntity.ok(
                "Task deleted successfully"
        );
    }
}