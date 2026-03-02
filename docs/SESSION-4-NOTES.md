# Session 4 Implementation Notes

## Features Implemented

### Task Assignments
Already included in Task entity:
- `assignee` field (email address)
- `tags` field (Set<String>)
- Validation for email format
- Full CRUD support via existing endpoints

### How to Use Assignments

#### Assign a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Review PR",
    "description": "Review the authentication PR",
    "assignee": "developer@example.com",
    "tags": ["code-review", "urgent"],
    "priority": "HIGH"
  }'
```

#### Update Assignment
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Review PR",
    "description": "Review the authentication PR",
    "assignee": "newdev@example.com",
    "tags": ["code-review", "in-progress"],
    "status": "IN_PROGRESS"
  }'
```

## MCP Integration

### NotionIntegrationService
A stub implementation is provided in:
`src/main/java/com/workshop/taskapi/integration/NotionIntegrationService.java`

This service demonstrates the integration pattern but requires actual MCP configuration in Session 4.

### What Participants Will Do

In Session 4, participants will:

1. **Configure MCP Server**
   - Set up Notion or Google Calendar MCP
   - Authenticate with the service
   - Test connection

2. **Implement Real Integration**
   - Replace TODO comments with actual MCP calls
   - Map Task fields to Notion properties or Calendar events
   - Test synchronization

3. **Add to TaskService**
   - Call integration service after create/update
   - Handle errors gracefully
   - Use async processing (optional)

### Example Integration Pattern

```java
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotionIntegrationService notionIntegration;

    public Task createTask(Task task) {
        Task saved = taskRepository.save(task);

        // Sync to Notion after saving
        try {
            notionIntegration.syncTaskToNotion(saved);
        } catch (Exception e) {
            log.warn("Failed to sync task to Notion", e);
            // Don't fail the main operation
        }

        return saved;
    }
}
```

## MCP Configuration

### Notion MCP Setup
1. Create Notion integration at https://www.notion.so/my-integrations
2. Get integration token
3. Create Tasks database in Notion
4. Configure Claude CLI with Notion MCP
5. Update NOTION_DATABASE_ID in NotionIntegrationService

### Google Calendar MCP Setup
1. Enable Google Calendar API in Google Cloud Console
2. Create OAuth 2.0 credentials
3. Configure Claude CLI with Calendar MCP
4. Implement calendar event creation in integration service

## Testing the Integration

### Manual Testing
```bash
# Create a task (should sync to Notion/Calendar)
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test MCP Sync",
    "description": "Should appear in Notion or Calendar",
    "dueDate": "2024-12-31",
    "priority": "HIGH"
  }'

# Check your Notion database or Google Calendar
# The task should appear there!
```

### Integration Test Pattern
```java
@Test
void shouldSyncTaskToNotionWhenCreated() {
    // This would require mocking the MCP service
    // or using a test Notion database

    Task task = new Task("Test", "Description");
    Task created = taskService.createTask(task);

    // Verify sync was attempted
    verify(notionIntegration).syncTaskToNotion(created);
}
```

## Best Practices

1. **Graceful Degradation**
   - MCP failures should not break main functionality
   - Log errors but don't throw exceptions
   - Consider retry logic for transient failures

2. **Async Processing**
   - Use @Async for non-critical sync operations
   - Don't make users wait for external API calls
   - Queue failed syncs for retry

3. **Configuration**
   - Externalize MCP settings (database IDs, API keys)
   - Use environment variables or application.properties
   - Don't commit secrets to git

4. **Monitoring**
   - Log all MCP operations
   - Track success/failure rates
   - Alert on sustained failures

## Complete Workflow Summary

Session 4 brings together everything learned:

1. **Planning** (Session 1)
   - PLAN.md guides all implementation
   - CLAUDE.md enforces consistency

2. **Implementation** (Session 2)
   - Service layer with business logic
   - REST API with proper conventions
   - Comprehensive testing

3. **Refinement** (Session 3)
   - Agent-driven code review
   - Filtering and search
   - Continuous improvement

4. **Integration** (Session 4)
   - External service connectivity
   - Assignment features
   - Production-ready system

## Workshop Complete! 🎉

Participants now have:
- ✅ Full-featured Task Management API
- ✅ Understanding of task-based development
- ✅ Experience with Claude Code features
- ✅ Real-world integration patterns
- ✅ Production-ready code quality

Ready to apply these skills to your own projects!
