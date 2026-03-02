package com.workshop.taskapi.exception;

/**
 * Exception thrown when a requested task is not found.
 */
public class TaskNotFoundException extends RuntimeException {

    private final Long taskId;

    /**
     * Constructs a new TaskNotFoundException.
     *
     * @param taskId the ID of the task that was not found
     */
    public TaskNotFoundException(Long taskId) {
        super("Task not found with id: " + taskId);
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }
}
