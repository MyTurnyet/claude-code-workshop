package com.workshop.taskapi.integration;

import com.workshop.taskapi.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service for integrating with Google Docs via MCP.
 * This is a stub implementation demonstrating the integration pattern.
 * In the actual workshop, participants will configure real MCP servers.
 */
@Service
public class GoogleDocsIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(GoogleDocsIntegrationService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // TODO: Configure your Google Doc ID from Session 4
    private static final String TASK_SUMMARY_DOC_ID = "your-google-doc-id-here";

    /**
     * Create or update a task summary document in Google Docs.
     * In Session 4, this will use the actual Google Docs MCP server.
     *
     * @param tasks the list of tasks to summarize
     */
    public void createTaskSummaryDocument(List<Task> tasks) {
        try {
            log.info("Creating task summary document with {} tasks", tasks.size());

            // TODO: In Session 4, implement actual MCP integration
            // Example pseudo-code:
            // String content = buildTaskSummaryContent(tasks);
            // googleDocsMcp.docs_get(TASK_SUMMARY_DOC_ID);
            // Update document with new content
            // Or search for existing doc and update it

            log.info("Successfully created task summary document");
        } catch (Exception e) {
            // Graceful degradation - don't fail main operation if doc creation fails
            log.error("Failed to create task summary document: {}", e.getMessage());
        }
    }

    /**
     * Append a task to the task summary document.
     *
     * @param task the task to append
     */
    public void appendTaskToDocument(Task task) {
        try {
            log.info("Appending task {} to Google Doc", task.getId());

            // TODO: In Session 4, implement MCP append
            // String taskEntry = formatTaskEntry(task);
            // googleDocsMcp.appendToDoc(TASK_SUMMARY_DOC_ID, taskEntry);

            log.info("Successfully appended task {} to Google Doc", task.getId());
        } catch (Exception e) {
            log.error("Failed to append task {} to Google Doc: {}", task.getId(), e.getMessage());
        }
    }

    /**
     * Update a task entry in the document.
     *
     * @param task the task to update
     */
    public void updateTaskInDocument(Task task) {
        try {
            log.info("Updating task {} in Google Doc", task.getId());

            // TODO: In Session 4, implement MCP update
            // Search for task entry and replace with updated version
            // Or recreate entire document

            log.info("Successfully updated task {} in Google Doc", task.getId());
        } catch (Exception e) {
            log.error("Failed to update task {} in Google Doc: {}", task.getId(), e.getMessage());
        }
    }

    /**
     * Search for task-related documents in Google Drive.
     *
     * @param searchQuery the search query
     * @return list of document IDs (stub - would return actual IDs from MCP)
     */
    public List<String> searchTaskDocuments(String searchQuery) {
        try {
            log.info("Searching for documents with query: {}", searchQuery);

            // TODO: In Session 4, implement MCP search
            // List<Document> results = googleDocsMcp.docs_search(
            //     query=searchQuery,
            //     max_results=10
            // );
            // return results.stream().map(doc -> doc.getId()).collect(Collectors.toList());

            log.info("Search completed");
            return List.of(); // Stub return
        } catch (Exception e) {
            log.error("Failed to search documents: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Helper method to format a task entry for the document.
     * This would be used when actually writing to Google Docs.
     *
     * @param task the task to format
     * @return formatted task entry
     */
    private String formatTaskEntry(Task task) {
        StringBuilder entry = new StringBuilder();
        entry.append("## ").append(task.getTitle()).append("\n\n");
        entry.append("**ID**: ").append(task.getId()).append("\n\n");
        entry.append("**Description**: ").append(task.getDescription()).append("\n\n");
        entry.append("**Status**: ").append(task.getStatus()).append("\n\n");
        entry.append("**Priority**: ").append(task.getPriority()).append("\n\n");

        if (task.getDueDate() != null) {
            entry.append("**Due Date**: ").append(task.getDueDate()).append("\n\n");
        }

        if (task.getAssignee() != null) {
            entry.append("**Assignee**: ").append(task.getAssignee()).append("\n\n");
        }

        if (task.getTags() != null && !task.getTags().isEmpty()) {
            entry.append("**Tags**: ").append(String.join(", ", task.getTags())).append("\n\n");
        }

        entry.append("**Created**: ").append(task.getCreatedAt().format(DATE_FORMATTER)).append("\n\n");
        entry.append("**Updated**: ").append(task.getUpdatedAt().format(DATE_FORMATTER)).append("\n\n");
        entry.append("---\n\n");

        return entry.toString();
    }

    /**
     * Build complete task summary content for the document.
     *
     * @param tasks list of tasks
     * @return formatted content
     */
    private String buildTaskSummaryContent(List<Task> tasks) {
        StringBuilder content = new StringBuilder();
        content.append("# Task Management Summary\n\n");
        content.append("Generated: ").append(LocalDateTime.now().format(DATE_FORMATTER)).append("\n\n");
        content.append("Total Tasks: ").append(tasks.size()).append("\n\n");

        // Group by status
        long pending = tasks.stream().filter(t -> t.getStatus().name().equals("PENDING")).count();
        long inProgress = tasks.stream().filter(t -> t.getStatus().name().equals("IN_PROGRESS")).count();
        long completed = tasks.stream().filter(t -> t.getStatus().name().equals("COMPLETED")).count();

        content.append("## Summary by Status\n\n");
        content.append("- **Pending**: ").append(pending).append("\n");
        content.append("- **In Progress**: ").append(inProgress).append("\n");
        content.append("- **Completed**: ").append(completed).append("\n\n");

        content.append("## All Tasks\n\n");

        for (Task task : tasks) {
            content.append(formatTaskEntry(task));
        }

        return content.toString();
    }
}
