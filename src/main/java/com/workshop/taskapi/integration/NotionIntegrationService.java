package com.workshop.taskapi.integration;

import com.workshop.taskapi.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for integrating with Notion via MCP.
 * This is a stub implementation demonstrating the integration pattern.
 * In the actual workshop, participants will configure real MCP servers.
 */
@Service
public class NotionIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(NotionIntegrationService.class);

    // TODO: Configure your Notion database ID from Session 4
    private static final String NOTION_DATABASE_ID = "your-notion-database-id-here";

    /**
     * Sync a task to Notion.
     * In Session 4, this will use the actual Notion MCP server.
     *
     * @param task the task to sync
     */
    public void syncTaskToNotion(Task task) {
        try {
            log.info("Syncing task {} to Notion database {}", task.getId(), NOTION_DATABASE_ID);

            // TODO: In Session 4, implement actual MCP integration
            // Example pseudo-code:
            // notionMcp.createPage(NOTION_DATABASE_ID, {
            //     "Title": task.getTitle(),
            //     "Description": task.getDescription(),
            //     "Status": task.getStatus().toString(),
            //     "Priority": task.getPriority().toString(),
            //     "Assignee": task.getAssignee()
            // });

            log.info("Successfully synced task {} to Notion", task.getId());
        } catch (Exception e) {
            // Graceful degradation - don't fail main operation if sync fails
            log.error("Failed to sync task {} to Notion: {}", task.getId(), e.getMessage());
        }
    }

    /**
     * Update a task in Notion.
     *
     * @param task the task to update
     */
    public void updateTaskInNotion(Task task) {
        try {
            log.info("Updating task {} in Notion", task.getId());

            // TODO: In Session 4, implement MCP update
            // notionMcp.updatePage(pageId, properties);

            log.info("Successfully updated task {} in Notion", task.getId());
        } catch (Exception e) {
            log.error("Failed to update task {} in Notion: {}", task.getId(), e.getMessage());
        }
    }

    /**
     * Delete a task from Notion.
     *
     * @param taskId the task ID
     */
    public void deleteTaskFromNotion(Long taskId) {
        try {
            log.info("Archiving task {} in Notion", taskId);

            // TODO: In Session 4, implement MCP delete/archive
            // notionMcp.archivePage(pageId);

            log.info("Successfully archived task {} in Notion", taskId);
        } catch (Exception e) {
            log.error("Failed to archive task {} in Notion: {}", taskId, e.getMessage());
        }
    }
}
