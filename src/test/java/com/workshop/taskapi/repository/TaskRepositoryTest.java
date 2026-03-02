package com.workshop.taskapi.repository;

import com.workshop.taskapi.model.Priority;
import com.workshop.taskapi.model.Task;
import com.workshop.taskapi.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for TaskRepository.
 */
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldSaveTaskSuccessfully() {
        // Arrange
        Task task = new Task("Test Task", "Test Description");

        // Act
        Task saved = taskRepository.save(task);

        // Assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Test Task");
        assertThat(saved.getDescription()).isEqualTo("Test Description");
        assertThat(saved.getStatus()).isEqualTo(TaskStatus.PENDING);
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldFindTaskByIdWhenExists() {
        // Arrange
        Task task = new Task("Find Me", "Description");
        Task saved = taskRepository.save(task);

        // Act
        Optional<Task> found = taskRepository.findById(saved.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Find Me");
    }

    @Test
    void shouldReturnEmptyWhenTaskNotFound() {
        // Act
        Optional<Task> found = taskRepository.findById(999L);

        // Assert
        assertThat(found).isEmpty();
    }

    @Test
    void shouldFindAllTasks() {
        // Arrange
        taskRepository.save(new Task("Task 1", "Description 1"));
        taskRepository.save(new Task("Task 2", "Description 2"));
        taskRepository.save(new Task("Task 3", "Description 3"));

        // Act
        List<Task> allTasks = taskRepository.findAll();

        // Assert
        assertThat(allTasks).hasSize(3);
    }

    @Test
    void shouldDeleteTaskSuccessfully() {
        // Arrange
        Task task = taskRepository.save(new Task("Delete Me", "Description"));
        Long taskId = task.getId();

        // Act
        taskRepository.deleteById(taskId);

        // Assert
        Optional<Task> deleted = taskRepository.findById(taskId);
        assertThat(deleted).isEmpty();
    }

    @Test
    void shouldFindTasksByStatus() {
        // Arrange
        Task pending1 = new Task("Pending 1", "Desc", TaskStatus.PENDING, Priority.HIGH);
        Task pending2 = new Task("Pending 2", "Desc", TaskStatus.PENDING, Priority.LOW);
        Task completed = new Task("Completed", "Desc", TaskStatus.COMPLETED, Priority.MEDIUM);

        taskRepository.save(pending1);
        taskRepository.save(pending2);
        taskRepository.save(completed);

        // Act
        List<Task> pendingTasks = taskRepository.findByStatus(TaskStatus.PENDING);

        // Assert
        assertThat(pendingTasks).hasSize(2);
        assertThat(pendingTasks).extracting(Task::getStatus)
                .containsOnly(TaskStatus.PENDING);
    }

    @Test
    void shouldSearchTasksByKeyword() {
        // Arrange
        taskRepository.save(new Task("Important Meeting", "Discuss project"));
        taskRepository.save(new Task("Code Review", "Review important changes"));
        taskRepository.save(new Task("Other Task", "Unrelated"));

        // Act
        List<Task> results = taskRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        "important", "important"
                );

        // Assert
        assertThat(results).hasSize(2);
    }

    @Test
    void shouldFindByStatusAndSearch() {
        // Arrange
        Task task1 = new Task("Important Bug", "Fix ASAP", TaskStatus.PENDING, Priority.HIGH);
        Task task2 = new Task("Important Feature", "Implement", TaskStatus.IN_PROGRESS, Priority.MEDIUM);
        Task task3 = new Task("Other Task", "Description", TaskStatus.PENDING, Priority.LOW);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        // Act - filter by status only
        List<Task> pendingOnly = taskRepository.findByStatusAndSearch(TaskStatus.PENDING, null);

        // Assert
        assertThat(pendingOnly).hasSize(2);

        // Act - search only
        List<Task> searchOnly = taskRepository.findByStatusAndSearch(null, "important");

        // Assert
        assertThat(searchOnly).hasSize(2);

        // Act - both filters
        List<Task> both = taskRepository.findByStatusAndSearch(TaskStatus.PENDING, "important");

        // Assert
        assertThat(both).hasSize(1);
        assertThat(both.get(0).getTitle()).isEqualTo("Important Bug");
    }
}
