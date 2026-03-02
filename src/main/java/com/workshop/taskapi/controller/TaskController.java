package com.workshop.taskapi.controller;

import com.workshop.taskapi.model.Task;
import com.workshop.taskapi.model.TaskStatus;
import com.workshop.taskapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Task Management API.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * Constructor with dependency injection.
     *
     * @param taskService the task service
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Create a new task.
     *
     * @param task the task to create
     * @return the created task with 201 Created status
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get all tasks with optional filtering.
     *
     * @param status the status to filter by (optional)
     * @param search the keyword to search for (optional)
     * @return list of tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String search
    ) {
        List<Task> tasks = taskService.getAllTasks(status, search);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get a task by ID.
     *
     * @param id the task ID
     * @return the task with 200 OK, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Update an existing task.
     *
     * @param id   the task ID
     * @param task the updated task data
     * @return the updated task with 200 OK, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody Task task
    ) {
        Task updated = taskService.updateTask(id, task);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a task.
     *
     * @param id the task ID
     * @return 204 No Content on success, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
