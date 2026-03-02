# Session 4: MCP Integration & Real-World Workflows

**Duration**: 30 minutes
**Goal**: Integrate external services via MCP and complete the full development workflow

---

## Learning Objectives

By the end of this session, you will:
- Understand Model Context Protocol (MCP)
- Configure and use MCP servers
- Integrate external services (Notion or Google Calendar)
- Use MCP during development, not just in production
- Complete the full task-based development cycle
- Summarize Claude Code best practices

---

## Key Concepts

### What is MCP (Model Context Protocol)?
- **Standard protocol** for connecting Claude to external tools and services
- **Bi-directional**: Read from and write to external services
- **Extensible**: Many pre-built servers, can create custom ones
- **Developer-Friendly**: Simple configuration, powerful capabilities

### MCP Use Cases
**During Development**:
- Save design decisions to Notion
- Track tasks in project management tools
- Access internal documentation
- Query databases or APIs

**In Production**:
- Sync application data with external services
- Trigger workflows in other systems
- Access real-time information
- Integrate with company tools

### Available MCP Servers
- **Notion**: Read/write Notion pages and databases
- **Google Calendar**: Manage calendar events
- **GitHub**: Access repositories, issues, PRs
- **Slack**: Send messages, read channels
- **File System**: Access local files
- **Many more**: Check MCP registry

---

## Step-by-Step Instructions

### Before You Start
- Session 3 complete (filtering works, agents used)
- Feature 4 plan from Plan agent
- Choose your MCP integration: Notion or Google Calendar

---

## Option A: Notion Integration Path

### Task A1: Configure Notion MCP (8 minutes)

**Prerequisites**:
- Notion account (free tier OK)
- Notion integration token

**Setup Steps**:

1. **Create Notion Integration**:
   ```
   - Go to https://www.notion.so/my-integrations
   - Click "New integration"
   - Name: "Task Management API"
   - Select workspace
   - Copy integration token
   ```

2. **Configure MCP Server**:
   ```bash
   # Configure Notion MCP in Claude
   # Follow Claude MCP configuration docs
   ```

3. **Test Connection**:
   ```
   Prompt Claude:
   "Use the Notion MCP to create a test page in my workspace
   with title 'MCP Test' and content 'Connection successful'."
   ```

**Success Check**:
- [ ] Notion integration created
- [ ] MCP server configured
- [ ] Test page created successfully

### Task A2: Create Notion Tasks Database (5 minutes)

**Goal**: Set up database for task sync

**Example Prompt**:
```
Using the Notion MCP, help me create a Notion database called "Tasks"
with these properties:
- Title (title)
- Description (text)
- Status (select: Pending, In Progress, Completed)
- Priority (select: Low, Medium, High)
- Due Date (date)
- Assignee (text)
- Created At (created time)

Provide the database ID for use in our integration.
```

**Alternative**: Create manually in Notion, then get database ID via MCP

**Success Check**:
- [ ] Tasks database exists in Notion
- [ ] All properties configured
- [ ] Database ID obtained

### Task A3: Implement Notion Integration Service (10 minutes)

**Goal**: Sync tasks to Notion

**Example Prompt**:
```
Following the Plan agent's design from Session 3, create NotionIntegrationService
in src/main/java/com/workshop/taskapi/integration/NotionIntegrationService.java

Features:
1. syncTaskToNotion(Task task) - Create/update page in Notion
2. Called automatically when task is created or updated
3. Map Task fields to Notion properties
4. Handle MCP errors gracefully
5. Log sync operations

Use the Notion MCP server we configured.
Follow CLAUDE.md conventions.

Notion database ID: [your-database-id]
```

**What Should Be Created**:
```java
@Service
public class NotionIntegrationService {

    private static final String NOTION_DATABASE_ID = "your-database-id";
    private static final Logger log = LoggerFactory.getLogger(NotionIntegrationService.class);

    public void syncTaskToNotion(Task task) {
        try {
            // Use Notion MCP to create/update page
            log.info("Syncing task {} to Notion", task.getId());
            // MCP call here
        } catch (Exception e) {
            log.error("Failed to sync task to Notion", e);
            // Don't throw - graceful degradation
        }
    }
}
```

**Update TaskService**:
```
Update TaskService to call NotionIntegrationService.syncTaskToNotion()
after creating or updating a task. Use async processing if possible.
```

**Success Check**:
- [ ] NotionIntegrationService created
- [ ] Integration with Notion MCP working
- [ ] Tasks sync to Notion automatically
- [ ] Error handling in place

### Task A4: Test Notion Integration (2 minutes)

**Verify**:
```bash
# Create a task via API
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title":"MCP Integration Test",
    "description":"Should appear in Notion",
    "status":"PENDING",
    "priority":"HIGH"
  }'

# Check Notion database - task should appear!
```

**Success Check**:
- [ ] Task created in API
- [ ] Task appears in Notion
- [ ] All fields mapped correctly

---

## Option B: Google Calendar Integration Path

### Task B1: Configure Google Calendar MCP (8 minutes)

**Prerequisites**:
- Google account
- Google Calendar API enabled

**Setup Steps**:

1. **Enable Calendar API**:
   ```
   - Go to Google Cloud Console
   - Create new project or select existing
   - Enable Google Calendar API
   - Create OAuth 2.0 credentials
   ```

2. **Configure MCP Server**:
   ```bash
   # Configure Google Calendar MCP in Claude
   # Follow Claude MCP configuration docs
   ```

3. **Test Connection**:
   ```
   Prompt Claude:
   "Use the Google Calendar MCP to create a test event
   for tomorrow at 2pm with title 'MCP Test'."
   ```

**Success Check**:
- [ ] Calendar API enabled
- [ ] MCP server configured
- [ ] Test event created successfully

### Task B2: Implement Calendar Integration Service (10 minutes)

**Goal**: Create calendar events from tasks with due dates

**Example Prompt**:
```
Following the Plan agent's design from Session 3, create CalendarIntegrationService
in src/main/java/com/workshop/taskapi/integration/CalendarIntegrationService.java

Features:
1. createCalendarEvent(Task task) - Create event for tasks with due dates
2. updateCalendarEvent(Task task) - Update existing event
3. deleteCalendarEvent(Task task) - Remove event when task completed
4. Called automatically when task is created/updated/completed
5. Handle MCP errors gracefully

Use the Google Calendar MCP server we configured.
Follow CLAUDE.md conventions.
```

**What Should Be Created**:
```java
@Service
public class CalendarIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(CalendarIntegrationService.class);

    public void createCalendarEvent(Task task) {
        if (task.getDueDate() == null) {
            return; // Only create events for tasks with due dates
        }

        try {
            log.info("Creating calendar event for task {}", task.getId());
            // Use Calendar MCP to create event
        } catch (Exception e) {
            log.error("Failed to create calendar event", e);
        }
    }

    public void updateCalendarEvent(Task task) {
        // Update existing event
    }

    public void deleteCalendarEvent(Task task) {
        // Delete event
    }
}
```

**Success Check**:
- [ ] CalendarIntegrationService created
- [ ] Integration with Calendar MCP working
- [ ] Events created for tasks with due dates
- [ ] Error handling in place

### Task B3: Test Calendar Integration (2 minutes)

**Verify**:
```bash
# Create a task with due date
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Important Meeting Prep",
    "description":"Prepare materials",
    "status":"PENDING",
    "dueDate":"2024-12-31"
  }'

# Check Google Calendar - event should appear!
```

**Success Check**:
- [ ] Task created with due date
- [ ] Calendar event created
- [ ] Event details correct

---

## Common Tasks (Both Paths)

### Task 5: Implement Task Assignments (5 minutes)

**Goal**: Add assignment feature to Task entity

**Example Prompt**:
```
Following the Plan agent's Feature 4 design, update the Task entity
to support assignments:

Add fields:
- assignee (String - email address)
- tags (Set<String> - categorization tags)

Add validation:
- assignee must be valid email format
- tags are optional

Update PLAN.md if the data model changed.
Follow CLAUDE.md conventions.
```

**Verify Schema**:
```bash
# Restart app to apply schema changes
mvn spring-boot:run

# Create task with assignment
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Review PR",
    "assignee":"developer@example.com",
    "tags":["code-review", "urgent"]
  }'
```

**Success Check**:
- [ ] Task entity updated
- [ ] Assignment fields work
- [ ] Validation applied
- [ ] Schema migrated

### Task 6: Use MCP During Development (3 minutes)

**Goal**: Demonstrate using MCP as part of workflow

**Example Prompt**:
```
Use the [Notion/Calendar] MCP integration to save development notes
about this workshop session:

Create a note titled "Session 4 - MCP Integration Complete" with:
- What we built (task assignments, MCP integration)
- Key learnings about MCP
- Challenges encountered
- Next steps

This demonstrates using MCP during development, not just in production code.
```

**Success Check**:
- [ ] Notes saved to external service
- [ ] Demonstrates workflow integration
- [ ] MCP value understood

---

## Complete Workflow Review

### Task 7: Workflow Walkthrough (2 minutes)

**Review the Complete Cycle**:

1. **Planning** (Session 1):
   - Created PLAN.md - source of truth
   - Created CLAUDE.md - automatic rule enforcement
   - Implemented foundation (entities, repositories)

2. **Implementation** (Session 2):
   - Built REST API following plan
   - Rules enforced automatically
   - Created custom skills for automation

3. **Advanced Features** (Session 3):
   - Used agents for complex tasks
   - Code review and improvement
   - Filter and search implementation

4. **Integration** (Session 4):
   - MCP configuration
   - External service integration
   - Task assignments
   - Complete workflow

**Prompt for Summary**:
```
Summarize the complete task-based development workflow we learned:
1. Planning phase (PLAN.md, CLAUDE.md)
2. Implementation phase (skills, agents)
3. Integration phase (MCP)
4. Key best practices
5. When to use each Claude Code feature
```

**Success Check**:
- [ ] Understand complete workflow
- [ ] Can explain each phase
- [ ] Know when to use each feature

---

## Troubleshooting

### Problem: MCP authentication fails
**Symptoms**: "Authentication error" when using MCP

**Solutions**:
1. Verify API keys/tokens are correct
2. Check token has necessary permissions
3. Regenerate token if expired
4. Review MCP configuration in Claude
5. Check service-specific requirements (OAuth, etc.)

### Problem: MCP commands don't work
**Symptoms**: Claude says MCP server not available

**Solutions**:
1. Verify MCP server is configured in Claude
2. Check server name is correct
3. Restart Claude CLI
4. Review MCP server logs
5. Test MCP connection independently

### Problem: Integration errors in production code
**Symptoms**: App throws errors when calling MCP

**Solutions**:
1. Add try-catch around MCP calls
2. Implement graceful degradation
3. Add retry logic for transient failures
4. Log errors but don't fail main operation
5. Consider async processing

### Problem: Tasks don't sync
**Symptoms**: Tasks created but don't appear in external service

**Solutions**:
1. Check service method is being called (add logging)
2. Verify external service credentials
3. Check field mapping is correct
4. Review external service API limits
5. Test MCP connection independently

---

## Success Criteria

Workshop complete when:
- [ ] MCP server configured and working
- [ ] External service integration functional
- [ ] Task assignments implemented
- [ ] All tests passing
- [ ] Can create task and see it in external service
- [ ] Understand complete Claude Code workflow
- [ ] Can explain when to use each feature

---

## Time Check

| Task | Target Time | Running Total |
|------|-------------|---------------|
| Configure MCP | 8 min | 8 min |
| Create Database/Setup | 5 min | 13 min |
| Implement Integration | 10 min | 23 min |
| Test Integration | 2 min | 25 min |
| Task Assignments | 5 min | 30 min |
| Use MCP in Development | 3 min | 33 min |
| Workflow Review | 2 min | 35 min |

**Note**: Slight overtime acceptable for final session.

---

## Key Takeaways

1. **MCP is Powerful**: Connects Claude to any external service
2. **Development Tool**: Use MCP during development, not just in production
3. **Graceful Degradation**: Handle MCP failures gracefully
4. **Complete Workflow**: Planning → Rules → Skills → Agents → MCP
5. **Task-Based Development**: Plan first, implement iteratively

---

## Best Practices Summary

### Planning
- Always create PLAN.md before coding
- Update plan as requirements evolve
- Reference plan explicitly in prompts
- Use plan to guide all decisions

### Rules
- Define CLAUDE.md early
- Make rules specific and actionable
- Rules apply automatically
- Update rules as team conventions evolve

### Skills
- Create skills for repeated tasks
- Share skills across team
- Keep skills focused and simple
- Document skill usage

### Agents
- Use appropriate agent for task type
- Review agent output carefully
- Combine agents for comprehensive work
- Let agents work autonomously

### MCP
- Configure carefully
- Handle errors gracefully
- Use during development
- Consider async processing

---

## What's Next?

### Post-Workshop Activities

1. **Explore Bonus Challenges**:
   - Add JWT authentication
   - Create more custom skills
   - Build complex agent workflows
   - Create custom MCP server

2. **Apply to Real Projects**:
   - Start with PLAN.md
   - Establish CLAUDE.md rules
   - Create team-specific skills
   - Use agents for code review

3. **Deepen Your Knowledge**:
   - Read Claude Code documentation
   - Explore MCP server catalog
   - Share learnings with team
   - Contribute to community

4. **Provide Feedback**:
   - What worked well?
   - What could be improved?
   - Which features will you use?
   - Any challenges encountered?

---

## Workshop Complete!

**Congratulations!** You've completed the Claude Code workshop and built a fully functional Task Management API using task-based development practices.

**You Now Know How To**:
- ✅ Create effective planning documents
- ✅ Enforce project rules automatically
- ✅ Build custom automation skills
- ✅ Use specialized agents effectively
- ✅ Integrate external services via MCP
- ✅ Follow task-based development workflow

**Your Workshop Project**:
- ✅ Task Management REST API
- ✅ Full CRUD operations
- ✅ Filtering and search
- ✅ Task assignments
- ✅ External service integration
- ✅ Comprehensive tests
- ✅ Production-ready code

**Next Steps**:
- Apply these techniques to your work
- Share knowledge with your team
- Explore advanced features
- Build amazing things with Claude Code!

---

Thank you for participating in the Claude Code Workshop! 🎉
