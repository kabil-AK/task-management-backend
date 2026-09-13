package com.example.taskmanagement.service;

import java.util.List;



import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.UserRepository;

import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.dto.TaskResponse;

@Service
public class TaskService {
	private final UserRepository userRepository;
	
    private final TaskRepository taskRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    
    private TaskResponse convertToResponse(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate()
        );
    }
    

    // CREATE
    public TaskResponse createTask(
            TaskRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return convertToResponse(savedTask);
    }

    // GET ALL
    public List<TaskResponse> getAllTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        return taskRepository.findByUser(user)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public TaskResponse getTaskById(
            Long id,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        Task task = taskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Task not found"
                    )
                );

        return convertToResponse(task);
    }
    // UPDATE
    public TaskResponse updateTask(
            Long id,
            TaskRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        Task existingTask = taskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Task not found"
                    )
                );

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setStatus(request.getStatus());
        existingTask.setDueDate(request.getDueDate());

        Task updatedTask = taskRepository.save(existingTask);

        return convertToResponse(updatedTask);
    }

    // DELETE
    public void deleteTask(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        Task existingTask = taskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Task not found"
                    )
                );

        taskRepository.delete(existingTask);
    }
}