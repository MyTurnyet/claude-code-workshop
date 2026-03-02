package com.workshop.taskapi.service;

import com.workshop.taskapi.exception.TaskNotFoundException;
import com.workshop.taskapi.model.Task;
import com.workshop.taskapi.model.TaskStatus;
import com.workshop.taskapi.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Task business logic.
 */
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * Constructor with dependency injection.
     *
     * @param taskRepository the task repository
     */
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Create a new task.
     *
     * @param task the task to create
     * @return the created task with generated ID
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Get a task by ID.
     *
     * @param id the task ID
     * @return the task
     * @throws TaskNotFoundException if task not found
     */
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    /**
     * Get all tasks, optionally filtered by status and search keyword.
     *
     * @param status the status to filter by (optional)
     * @param search the keyword to search for (optional)
     * @return list of matching tasks
     */
    @Transactional(readOnly = true)
    public List<Task> getAllTasks(TaskStatus status, String search) {
        if (status == null && search == null) {
            return taskRepository.findAll();
        }
        return taskRepository.findByStatusAndSearch(status, search);
    }

    /**
     * Get all tasks without filters.
     *
     * @return list of all tasks
     */
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Update an existing task.
     *
     * @param id   the task ID
     * @param task the updated task data
     * @return the updated task
     * @throws TaskNotFoundException if task not found
     */
    public Task updateTask(Long id, Task task) {
        Task existingTask = getTaskById(id);

        // Update fields
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setAssignee(task.getAssignee());

        if (task.getTags() != null) {
            existingTask.getTags().clear();
            existingTask.getTags().addAll(task.getTags());
        }

        return taskRepository.save(existingTask);
    }

    /**
     * Delete a task.
     *
     * @param id the task ID
     * @throws TaskNotFoundException if task not found
     */
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}
