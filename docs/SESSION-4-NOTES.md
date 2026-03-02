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

### GoogleDocsIntegrationService
A stub implementation is provided in:
`src/main/java/com/workshop/taskapi/integration/GoogleDocsIntegrationService.java`

This service demonstrates the integration pattern but requires actual MCP configuration in Session 4.

### What Participants Will Do

In Session 4, participants will:

1. **Configure Google MCP Server**
   - Google MCP is already available in Claude CLI!
   - Authenticate with Google account (automatic)
   - Test connection with docs_search
   - Create or identify a Google Doc for task summaries

2. **Implement Real Integration**
   - Replace TODO comments with actual MCP calls
   - Use `mcp__google__docs_search` to find documents
   - Use `mcp__google__docs_get` to read document content
   - Format tasks as markdown for Google Docs
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
    private final GoogleDocsIntegrationService googleDocsIntegration;

    public Task createTask(Task task) {
        Task saved = taskRepository.save(task);

        // Append to Google Doc after saving
        try {
            googleDocsIntegration.appendTaskToDocument(saved);
        } catch (Exception e) {
            log.warn("Failed to append task to Google Doc", e);
            // Don't fail the main operation
        }

        return saved;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        // Create summary document
        try {
            googleDocsIntegration.createTaskSummaryDocument(tasks);
        } catch (Exception e) {
            log.warn("Failed to create summary document", e);
        }

        return tasks;
    }
}
```

## MCP Configuration

### Google Docs MCP Setup

**Good News**: Google MCP is already included with Claude CLI! No separate installation needed.

**Setup Steps**:

1. **Authentication** (automatic):
   - Google MCP uses your Google account
   - Authentication happens when you first use it with Claude

2. **Test the connection**:
   ```
   Prompt Claude:
   "Use mcp__google__docs_search to search for documents with 'task' in my Google Drive."
   ```

3. **Create a Task Summary Document**:
   - Create a new Google Doc called "Task Management Summary"
   - Get the document ID from the URL:
     `https://docs.google.com/document/d/YOUR-DOC-ID-HERE/edit`
   - Update `TASK_SUMMARY_DOC_ID` in GoogleDocsIntegrationService

4. **Available Google MCP Tools**:
   - `mcp__google__docs_search(query, max_results)` - Search Google Drive
   - `mcp__google__docs_get(file_id)` - Get document content
   - `mcp__google__meeting_notes_search(query)` - Search meeting notes
   - `mcp__google__calendar_events` - Calendar integration (alternative option)
   - `mcp__google__calendar_list` - List calendars (alternative option)

## Testing the Integration

### Manual Testing with Google Docs
```bash
# Create a task (should append to Google Doc)
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test MCP Sync",
    "description": "Should appear in Google Doc",
    "dueDate": "2024-12-31",
    "priority": "HIGH"
  }'

# Get all tasks (should create/update summary document)
curl http://localhost:8080/api/tasks

# Check your Google Doc - the task entries should appear!
```

### Testing Google MCP Directly
```
# Search for documents
"Use mcp__google__docs_search to find documents with 'task' in the title"

# Get a specific document
"Use mcp__google__docs_get with file_id 'YOUR-DOC-ID' to read the Task Summary document"

# Verify task was added
"Show me the contents of my Task Management Summary document"
```

### Integration Test Pattern
```java
@Test
void shouldAppendTaskToGoogleDocWhenCreated() {
    // This would require mocking the MCP service
    // or using a test Google Doc

    Task task = new Task("Test", "Description");
    Task created = taskService.createTask(task);

    // Verify append was attempted
    verify(googleDocsIntegration).appendTaskToDocument(created);
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
