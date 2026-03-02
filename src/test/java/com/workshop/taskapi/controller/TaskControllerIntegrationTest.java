package com.workshop.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.taskapi.model.Priority;
import com.workshop.taskapi.model.Task;
import com.workshop.taskapi.model.TaskStatus;
import com.workshop.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for TaskController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void shouldCreateTaskWhenValidDataProvided() throws Exception {
        // Arrange
        Task task = new Task("Test Task", "Test Description");
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(Priority.HIGH);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void shouldReturnBadRequestWhenTitleMissing() throws Exception {
        // Arrange
        Task task = new Task();
        task.setDescription("Description without title");

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        // Arrange
        taskRepository.save(new Task("Task 1", "Description 1"));
        taskRepository.save(new Task("Task 2", "Description 2"));

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void shouldFilterTasksByStatus() throws Exception {
        // Arrange
        Task pending = new Task("Pending Task", "Desc", TaskStatus.PENDING, Priority.MEDIUM);
        Task completed = new Task("Completed Task", "Desc", TaskStatus.COMPLETED, Priority.LOW);
        taskRepository.save(pending);
        taskRepository.save(completed);

        // Act & Assert
        mockMvc.perform(get("/api/tasks")
                        .param("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Pending Task"));
    }

    @Test
    void shouldSearchTasksByKeyword() throws Exception {
        // Arrange
        taskRepository.save(new Task("Important Meeting", "Discuss project"));
        taskRepository.save(new Task("Code Review", "Review important changes"));
        taskRepository.save(new Task("Other Task", "Unrelated"));

        // Act & Assert
        mockMvc.perform(get("/api/tasks")
                        .param("search", "important"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetTaskByIdWhenExists() throws Exception {
        // Arrange
        Task saved = taskRepository.save(new Task("Find Me", "Description"));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.title").value("Find Me"));
    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Task Not Found"));
    }

    @Test
    void shouldUpdateTaskWhenExists() throws Exception {
        // Arrange
        Task existing = taskRepository.save(new Task("Old Title", "Old Description"));

        Task updated = new Task("New Title", "New Description");
        updated.setStatus(TaskStatus.COMPLETED);

        // Act & Assert
        mockMvc.perform(put("/api/tasks/" + existing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.description").value("New Description"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentTask() throws Exception {
        // Arrange
        Task updated = new Task("New Title", "New Description");

        // Act & Assert
        mockMvc.perform(put("/api/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTaskWhenExists() throws Exception {
        // Arrange
        Task saved = taskRepository.save(new Task("Delete Me", "Description"));

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/" + saved.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        mockMvc.perform(get("/api/tasks/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentTask() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/tasks/999"))
                .andExpect(status().isNotFound());
    }
}
