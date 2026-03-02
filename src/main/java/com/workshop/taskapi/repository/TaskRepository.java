package com.workshop.taskapi.repository;

import com.workshop.taskapi.model.Task;
import com.workshop.taskapi.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Task entity providing database access operations.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all tasks with the specified status.
     *
     * @param status the task status to filter by
     * @return list of tasks with the given status
     */
    List<Task> findByStatus(TaskStatus status);

    /**
     * Search tasks by keyword in title or description (case-insensitive).
     *
     * @param titleKeyword       keyword to search in title
     * @param descriptionKeyword keyword to search in description
     * @return list of matching tasks
     */
    List<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String titleKeyword,
            String descriptionKeyword
    );

    /**
     * Find tasks by status and/or search keyword.
     * Both parameters are optional (can be null).
     *
     * @param status the status to filter by (optional)
     * @param search the keyword to search for (optional)
     * @return list of matching tasks
     */
    @Query("SELECT t FROM Task t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:search IS NULL OR " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Task> findByStatusAndSearch(
            @Param("status") TaskStatus status,
            @Param("search") String search
    );
}
