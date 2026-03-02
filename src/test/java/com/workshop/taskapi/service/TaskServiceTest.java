package com.workshop.taskapi.service;

import com.workshop.taskapi.exception.TaskNotFoundException;
import com.workshop.taskapi.model.Priority;
import com.workshop.taskapi.model.Task;
import com.workshop.taskapi.model.TaskStatus;
import com.workshop.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TaskService.
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldCreateTaskSuccessfully() {
        // Arrange
        Task task = new Task("New Task", "Description");
        Task savedTask = new Task("New Task", "Description");
        savedTask.setId(1L);

        when(taskRepository.save(task)).thenReturn(savedTask);

        // Act
        Task result = taskService.createTask(task);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("New Task");
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void shouldGetTaskByIdWhenExists() {
        // Arrange
        Task task = new Task("Existing Task", "Description");
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        Task result = taskService.getTaskById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Existing Task");
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.getTaskById(999L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task not found with id: 999");
    }

    @Test
    void shouldGetAllTasksWithoutFilters() {
        // Arrange
        List<Task> tasks = Arrays.asList(
                new Task("Task 1", "Desc 1"),
                new Task("Task 2", "Desc 2")
        );
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<Task> result = taskService.getAllTasks(null, null);

        // Assert
        assertThat(result).hasSize(2);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void shouldGetAllTasksWithFilters() {
        // Arrange
        List<Task> tasks = Arrays.asList(
                new Task("Important Task", "Desc", TaskStatus.PENDING, Priority.HIGH)
        );
        when(taskRepository.findByStatusAndSearch(TaskStatus.PENDING, "important"))
                .thenReturn(tasks);

        // Act
        List<Task> result = taskService.getAllTasks(TaskStatus.PENDING, "important");

        // Assert
        assertThat(result).hasSize(1);
        verify(taskRepository, times(1)).findByStatusAndSearch(TaskStatus.PENDING, "important");
    }

    @Test
    void shouldUpdateTaskSuccessfully() {
        // Arrange
        Task existingTask = new Task("Old Title", "Old Desc");
        existingTask.setId(1L);

        Task updatedData = new Task("New Title", "New Desc");
        updatedData.setStatus(TaskStatus.COMPLETED);
        updatedData.setPriority(Priority.HIGH);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        // Act
        Task result = taskService.updateTask(1L, updatedData);

        // Assert
        assertThat(result.getTitle()).isEqualTo("New Title");
        assertThat(result.getDescription()).isEqualTo("New Desc");
        assertThat(result.getStatus()).isEqualTo(TaskStatus.COMPLETED);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentTask() {
        // Arrange
        Task updatedData = new Task("New Title", "New Desc");
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.updateTask(999L, updatedData))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void shouldDeleteTaskSuccessfully() {
        // Arrange
        when(taskRepository.existsById(1L)).thenReturn(true);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        // Arrange
        when(taskRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> taskService.deleteTask(999L))
                .isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository, never()).deleteById(999L);
    }
}
