# Claude Code Workshop Guide

Complete step-by-step instructions for all four workshop sessions. Follow these instructions to build a Task Management REST API using Claude Code's task-based development approach.

---

## Session 1: Planning-Driven Development (25 minutes)

### Learning Objectives
- Create effective planning documents that guide implementation
- Establish project rules for consistency
- Use plans to direct Claude's code generation
- Implement basic entities following the plan

### Concepts Introduced
- `PLAN.md` - Project planning document as source of truth
- `CLAUDE.md` - Project-specific rules and conventions
- Task-based development workflow
- Basic Claude CLI interaction

### Step-by-Step Tasks

#### Task 1.1: Create Your Planning Document (8 minutes)

**Prompt Claude:**
```
Help me create a comprehensive PLAN.md for a Task Management REST API.
Include the following sections:
- Project Overview (purpose, goals, technical stack)
- Features List (4 features we'll build across sessions)
- API Endpoints (complete REST API design)
- Data Model (Task entity with all fields)
- Testing Strategy (what and how to test)

Make it detailed enough to guide implementation throughout the workshop.
```

**Expected Outcome:**
- `/Users/paige.watson/IdeaProjects/claude-code-workshop/PLAN.md` created
- Plan includes all 4 features (CRUD, REST API, search/filter, assignments)
- Clear API endpoint specifications
- Detailed data model for Task entity

**Tip:** You can reference `docs/PLAN-template.md` for structure or `docs/PLAN-complete.md` for a complete example.

#### Task 1.2: Create Project Rules (5 minutes)

**Prompt Claude:**
```
Create a CLAUDE.md rules file for this Spring Boot project. Include:
- Java code style (naming conventions, formatting)
- Spring Boot conventions (Controller/Service/Repository patterns)
- Testing requirements (JUnit 5, test naming, coverage expectations)
- Commit message format (conventional commits)
- Documentation standards (when to add Javadoc)

These rules should be enforced automatically throughout development.
```

**Expected Outcome:**
- `/Users/paige.watson/IdeaProjects/claude-code-workshop/CLAUDE.md` created
- Clear, actionable rules
- Rules cover code style, architecture, testing, and documentation

**Tip:** Reference `docs/CLAUDE-template.md` for examples. Customize rules to your preferences!

#### Task 1.3: Implement Task Entity (8 minutes)

**Prompt Claude:**
```
Following the PLAN.md, implement the Task entity in src/main/java/com/workshop/taskapi/model/Task.java.

Requirements from PLAN.md:
- Use JPA annotations (@Entity, @Id, @GeneratedValue, etc.)
- Include all fields defined in the data model section
- Add validation annotations where appropriate
- Follow the rules in CLAUDE.md for code style
- Include proper getters, setters, and constructors
```

**Expected Outcome:**
- `src/main/java/com/workshop/taskapi/model/Task.java` created
- Entity has proper JPA annotations
- Includes fields: id, title, description, status, createdAt, updatedAt (or similar based on your plan)
- Code follows CLAUDE.md rules

**Verification:**
```bash
# Compile the code
mvn clean compile

# Should succeed without errors
```

#### Task 1.4: Create Repository Layer (2 minutes)

**Prompt Claude:**
```
Create the TaskRepository interface in src/main/java/com/workshop/taskapi/repository/TaskRepository.java.

It should extend JpaRepository and provide basic CRUD operations.
Follow CLAUDE.md conventions.
```

**Expected Outcome:**
- `src/main/java/com/workshop/taskapi/repository/TaskRepository.java` created
- Extends JpaRepository<Task, Long>
- Includes any custom query methods if specified in plan

#### Task 1.5: Write Unit Tests (2 minutes)

**Prompt Claude:**
```
Create a basic repository test in src/test/java/com/workshop/taskapi/TaskRepositoryTest.java
that verifies saving and retrieving tasks. Follow testing rules from CLAUDE.md.
```

**Expected Outcome:**
- Test class created with @DataJpaTest annotation
- At least one test for save and findById operations
- Tests pass

**Verification:**
```bash
# Run all tests
mvn test

# All tests should pass (including BasicSetupTest)
```

### Success Criteria
- [ ] PLAN.md created with comprehensive project plan
- [ ] CLAUDE.md created with clear project rules
- [ ] Task entity implemented with JPA annotations
- [ ] TaskRepository interface created
- [ ] Unit tests written and passing
- [ ] Code compiles successfully
- [ ] All code follows CLAUDE.md conventions

### Troubleshooting
- **Issue**: Claude doesn't follow CLAUDE.md rules
  - **Solution**: Explicitly mention "Follow the rules in CLAUDE.md" in your prompts

- **Issue**: Compilation errors in Task entity
  - **Solution**: Check that JPA dependencies are in pom.xml and imports are correct

- **Issue**: Tests fail
  - **Solution**: Verify H2 database is configured in application.properties

### Checkpoint
If you're behind schedule or experiencing issues:
```bash
git checkout session-1-complete
```

This gives you a working starting point for Session 2.

---

## Session 2: Rules & Skills (25 minutes)

### Learning Objectives
- Understand how CLAUDE.md enforces consistency automatically
- Create custom skills for repeated workflows
- Build REST API following planning documents
- Reference specific sections of planning documents

### Concepts Introduced
- Automatic rule enforcement
- Custom skill creation and syntax
- REST controller patterns
- Integration testing

### Step-by-Step Tasks

#### Task 2.1: Implement Service Layer (7 minutes)

**Prompt Claude:**
```
Following PLAN.md section on Business Logic, create TaskService in
src/main/java/com/workshop/taskapi/service/TaskService.java.

Implement all CRUD operations:
- Create new task
- Get task by ID
- Get all tasks
- Update task
- Delete task

Follow CLAUDE.md rules and use proper error handling.
```

**Expected Outcome:**
- TaskService class with @Service annotation
- All CRUD methods implemented
- Proper exception handling
- Dependency injection of TaskRepository

#### Task 2.2: Build REST Controller (8 minutes)

**Prompt Claude:**
```
Following PLAN.md section 'API Endpoints', implement TaskController in
src/main/java/com/workshop/taskapi/controller/TaskController.java.

Create these endpoints:
- POST /api/tasks - Create task
- GET /api/tasks - Get all tasks
- GET /api/tasks/{id} - Get task by ID
- PUT /api/tasks/{id} - Update task
- DELETE /api/tasks/{id} - Delete task

Use proper HTTP status codes and follow CLAUDE.md REST conventions.
```

**Expected Outcome:**
- TaskController with @RestController and @RequestMapping
- All 5 endpoints implemented
- Proper HTTP status codes (200, 201, 204, 404)
- Request/response body mappings

**Verification:**
```bash
# Start the application
mvn spring-boot:run

# In another terminal, test endpoints
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Task","description":"Testing API"}'
```

#### Task 2.3: Write Integration Tests (5 minutes)

**Prompt Claude:**
```
Create integration tests in src/test/java/com/workshop/taskapi/TaskControllerTest.java
that test all REST endpoints. Use @SpringBootTest and TestRestTemplate or MockMvc.
Follow testing guidelines in CLAUDE.md.
```

**Expected Outcome:**
- Integration test class created
- Tests for all CRUD endpoints
- Assertions verify correct behavior and status codes
- All tests pass

#### Task 2.4: Create Custom Skill (5 minutes)

**Prompt Claude:**
```
Create a custom Claude Code skill called "api-endpoint" that scaffolds a new REST endpoint.

The skill should:
1. Create a controller method with proper annotations
2. Create a corresponding service method
3. Create basic integration tests
4. Follow CLAUDE.md conventions

Save it in .claude/skills/api-endpoint/
```

**Expected Outcome:**
- `.claude/skills/api-endpoint/` directory created
- Skill includes instructions for creating endpoints
- Skill can be invoked with `/api-endpoint`

**Tip:** Skills are reusable automation scripts. This skill will help create future endpoints faster.

### Success Criteria
- [ ] TaskService implemented with all CRUD operations
- [ ] TaskController created with 5 REST endpoints
- [ ] API endpoints work correctly (verified with curl or Postman)
- [ ] Integration tests written and passing
- [ ] Custom `/api-endpoint` skill created
- [ ] All code follows CLAUDE.md rules consistently

### Troubleshooting
- **Issue**: Controller returns 404
  - **Solution**: Check @RequestMapping path and ensure Spring Boot component scanning is working

- **Issue**: Integration tests fail
  - **Solution**: Verify test configuration and that H2 database is properly set up for tests

- **Issue**: Skill doesn't work
  - **Solution**: Check skill file structure and syntax. See Claude CLI skill documentation.

### Checkpoint
```bash
git checkout session-2-complete
```

---

## Session 3: Agents (25 minutes)

### Learning Objectives
- Understand different agent types (Explore, Plan, code-review)
- Know when to use agents vs direct commands
- Use agents for complex tasks like code review and planning
- Incorporate agent feedback into code

### Concepts Introduced
- Specialized agents for different tasks
- Agent-driven development workflows
- Code review automation
- Parallel agent execution

### Step-by-Step Tasks

#### Task 3.1: Plan Feature 3 with Plan Agent (5 minutes)

**Prompt Claude:**
```
Use a Plan agent to design the task filtering and search feature.
The agent should analyze our current codebase and create a detailed plan for:
- Filter tasks by status
- Search tasks by title/description
- Query parameters for the GET /api/tasks endpoint

Launch the agent and review its recommendations.
```

**Expected Outcome:**
- Plan agent provides detailed implementation strategy
- Recommendations for query methods in repository
- Controller changes for filter/search parameters
- Testing approach

**Tip:** Agents work autonomously. Let them explore the codebase and provide recommendations.

#### Task 3.2: Implement Search and Filter (8 minutes)

**Prompt Claude:**
```
Following the Plan agent's recommendations, implement task filtering and search.

Add to TaskRepository:
- Query methods for filtering by status
- Query methods for searching by title/description

Update TaskController:
- Add query parameters to GET /api/tasks endpoint
- Support ?status=COMPLETED and ?search=keyword

Follow CLAUDE.md rules.
```

**Expected Outcome:**
- Repository has custom query methods (using @Query or query derivation)
- Controller handles query parameters
- Filtering and search work correctly

**Verification:**
```bash
# Test filtering
curl "http://localhost:8080/api/tasks?status=COMPLETED"

# Test search
curl "http://localhost:8080/api/tasks?search=important"
```

#### Task 3.3: Code Review with Agent (5 minutes)

**Prompt Claude:**
```
Launch a code-review agent to review the TaskService class for:
- Potential bugs
- Performance issues
- Code quality improvements
- Best practices violations

Review the agent's findings and make recommended improvements.
```

**Expected Outcome:**
- Code-review agent provides detailed feedback
- Identifies any issues or improvement opportunities
- Suggestions for refactoring

#### Task 3.4: Refactor Based on Feedback (5 minutes)

**Prompt Claude:**
```
Implement the improvements suggested by the code-review agent.
Focus on the highest priority items and maintain CLAUDE.md compliance.
```

**Expected Outcome:**
- Code refactored based on agent feedback
- Tests still pass after refactoring
- Code quality improved

#### Task 3.5: Plan Next Feature with Agent (2 minutes)

**Prompt Claude:**
```
Use a Plan agent to design Feature 4: Task assignments and tags.
The agent should consider:
- Data model changes (relationships)
- New API endpoints needed
- Integration opportunities for Session 4 (MCP with Notion or Calendar)

We'll implement this in Session 4.
```

**Expected Outcome:**
- Detailed plan for assignments feature
- Data model recommendations
- API design for new endpoints
- Preparation for MCP integration

### Success Criteria
- [ ] Search and filter feature implemented and working
- [ ] Code review completed with agent
- [ ] Refactoring applied based on feedback
- [ ] Tests still pass after all changes
- [ ] Plan for Feature 4 created by agent
- [ ] Understanding of when to use each agent type

### Agent Decision Matrix

| Task Type | Agent to Use | Why |
|-----------|--------------|-----|
| Explore codebase patterns | Explore | Fast, thorough code search |
| Design new feature | Plan | Architectural thinking |
| Review code quality | code-review | Catches bugs and improvements |
| Complex refactoring | Plan + code-review | Design then validate |
| Learning existing code | Explore | Understands patterns quickly |

### Troubleshooting
- **Issue**: Agent takes too long
  - **Solution**: Be more specific in your prompt to narrow scope

- **Issue**: Agent feedback unclear
  - **Solution**: Ask follow-up questions about specific recommendations

- **Issue**: Refactoring breaks tests
  - **Solution**: Use checkpoint branch and try again with smaller changes

### Checkpoint
```bash
git checkout session-3-complete
```

---

## Session 4: MCP Integration (30 minutes)

### Learning Objectives
- Understand Model Context Protocol (MCP)
- Integrate external services into development workflow
- Use MCP during development (not just in final code)
- Complete full task-based development cycle

### Concepts Introduced
- MCP server configuration
- External service integration (Notion or Google Calendar)
- Using MCP tools during development
- End-to-end workflow

### Step-by-Step Tasks

#### Task 4.1: Test Google Docs MCP Connection (5 minutes)

**Good News**: Google MCP is already included with Claude CLI!

**Setup:**
No installation needed - Google MCP comes with Claude CLI.

**Prompt Claude:**
```
Test the Google Docs MCP by searching for documents in my Google Drive.

Use mcp__google__docs_search with query "task" to find any documents
related to tasks. Show me the results.
```

**Expected Outcome:**
- Google MCP responds successfully
- Can see documents from your Google Drive
- Document IDs are visible
- MCP is ready to use!

**Create Task Summary Document:**
```
Help me create or identify a Google Doc for storing task summaries.
I need to get the document ID from the URL so I can configure it
in my integration service.
```

Or manually:
1. Go to https://docs.google.com
2. Create new document: "Task Management Summary"
3. Copy document ID from URL: `https://docs.google.com/document/d/YOUR-DOC-ID/edit`

#### Task 4.2: Implement Task Assignments Feature (10 minutes)

**Prompt Claude:**
```
Following the plan created by the agent in Session 3, implement task assignments:

1. Update Task entity to support assignments and tags
2. Create assignment-related endpoints
3. Update service layer for assignment logic
4. Add tests for new functionality

Follow PLAN.md and CLAUDE.md rules.
```

**Expected Outcome:**
- Task entity updated with assignment fields
- New endpoints for managing assignments
- Tests cover new functionality
- All tests pass

#### Task 4.3: Integrate Google Docs MCP Service (10 minutes)

**Prompt Claude:**
```
Update GoogleDocsIntegrationService in
src/main/java/com/workshop/taskapi/integration/GoogleDocsIntegrationService.java

Features:
1. appendTaskToDocument(Task task) - Add task entry to Google Doc
2. createTaskSummaryDocument(List<Task> tasks) - Generate full summary
3. Format tasks as markdown (## Title, **Status**, etc.)
4. Handle MCP errors gracefully
5. Log all operations

Use these Google MCP tools:
- mcp__google__docs_search(query, max_results)
- mcp__google__docs_get(file_id)

Follow CLAUDE.md conventions.

My Task Summary Google Doc ID: [your-doc-id-here]
```

**Expected Outcome:**
- GoogleDocsIntegrationService updated with MCP calls
- Tasks format as markdown
- Sync functionality works
- Error handling in place

**Verification:**
```bash
# Create a task (should append to Google Doc)
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Test MCP Sync","description":"Should appear in Google Doc"}'

# Get all tasks (should create summary document)
curl http://localhost:8080/api/tasks

# Check your Google Doc - task should appear!
```

**Verify with Claude:**
```
Use mcp__google__docs_get with file_id "[your-doc-id]" to read my
Task Management Summary document and show me what's in it.
```

#### Task 4.4: Use MCP During Development (4 minutes)

**Prompt Claude:**
```
Use the Google Docs MCP integration during development:

1. Save design decisions to Google Docs as we implement
2. Document any issues encountered in a Google Doc
3. Create a "Workshop Complete" task and sync it to the summary document

This demonstrates using MCP as part of the development workflow, not just in production code.
```

**Expected Outcome:**
- Development notes saved to Google Docs
- Demonstrates MCP value in workflow
- Workshop completion documented
- Can search and retrieve notes later with MCP

### Complete Workflow Review

**Prompt Claude:**
```
Walk me through the complete task-based development workflow we've learned:

1. Planning (PLAN.md creation)
2. Rules setup (CLAUDE.md)
3. Implementation (following plan)
4. Skills creation (automation)
5. Agent usage (review, planning)
6. MCP integration (external services)

Summarize key takeaways and best practices.
```

### Success Criteria
- [ ] MCP server configured and working
- [ ] Task assignments feature implemented
- [ ] MCP integration working (sync to Notion or Calendar)
- [ ] All tests passing
- [ ] Complete workflow demonstrated
- [ ] Can explain when to use each Claude Code feature

### Troubleshooting
- **Issue**: MCP authentication fails
  - **Solution**: Check API keys, permissions, and MCP server logs

- **Issue**: Sync doesn't work
  - **Solution**: Verify MCP connection, check error logs, test MCP commands manually

- **Issue**: Integration tests fail
  - **Solution**: May need to mock MCP service for tests

### Checkpoint
```bash
git checkout session-4-complete
```

This is the final complete solution with all features implemented!

---

## Post-Workshop: Next Steps

### 1. Review and Reflect
- What worked well with Claude Code?
- Which features will you use in your projects?
- What challenges did you encounter?

### 2. Explore Bonus Challenges
See `docs/BONUS-CHALLENGES.md` for additional exercises:
- Add JWT authentication
- Create more custom skills
- Build complex agent workflows
- Create custom MCP server

### 3. Apply to Real Projects
- Start with planning documents (PLAN.md)
- Establish project rules (CLAUDE.md)
- Create team-specific skills
- Use agents for code review

### 4. Share Your Experience
- Share learnings with your team
- Contribute skills and patterns to your organization
- Provide feedback on the workshop

---

## Workshop Summary

### Key Takeaways

**Task-Based Development:**
- Planning documents guide implementation
- Rules enforce consistency automatically
- Iterate with feedback loops

**Claude Code Features:**
1. **Planning Documents** (PLAN.md) - Source of truth for implementation
2. **Rules** (CLAUDE.md) - Automatic enforcement of conventions
3. **Skills** - Reusable automation for repeated tasks
4. **Agents** - Specialized help for complex work
5. **MCP** - External service integration

**Best Practices:**
- Create comprehensive plans before coding
- Reference plans explicitly in prompts
- Use rules to maintain consistency
- Choose the right agent for the task
- Integrate MCP into development workflow

### Time Breakdown
- Session 1: Planning & Foundation (25 min)
- Session 2: Implementation & Automation (25 min)
- Session 3: Advanced Features & Agents (25 min)
- Session 4: Integration & Workflow (30 min)
- **Total**: ~105 minutes + 10 min buffer

---

## Additional Resources

- **Claude Code Documentation**: https://docs.anthropic.com/claude/docs/claude-cli
- **Spring Boot Guides**: https://spring.io/guides
- **MCP Specification**: https://modelcontextprotocol.io/
- **Workshop Materials**: See `docs/` folder for templates and guides

---

**Congratulations!** You've completed the Claude Code workshop and built a complete Task Management API using task-based development practices. You're now ready to apply these techniques to your own projects!
