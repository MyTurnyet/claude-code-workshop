# Session 2: Iterative Development with Rules & Skills

**Duration**: 25 minutes
**Goal**: Build REST API following the plan and create custom automation skills

---

## Learning Objectives

By the end of this session, you will:
- Implement service and controller layers following PLAN.md
- See CLAUDE.md rules enforced automatically
- Create and use custom Claude Code skills
- Write integration tests for REST APIs
- Reference specific sections of planning documents

---

## Key Concepts

### Automatic Rule Enforcement
- Claude Code reads CLAUDE.md and applies rules without being asked
- Rules persist across all sessions
- Consistency happens automatically
- Focus on logic, not conventions

### Custom Skills
- **What**: Reusable automation scripts for common tasks
- **Why**: Reduce repetition, standardize patterns, speed up development
- **When**: Create skills for tasks you do repeatedly
- **Where**: `.claude/skills/` directory

### Service Layer Pattern
- Business logic separate from web layer
- Transactional boundaries
- Coordinate between repositories
- Easier to test than controllers

---

## Step-by-Step Instructions

### Before You Start
- Ensure Session 1 is complete (Task entity, repository, tests passing)
- PLAN.md and CLAUDE.md should exist
- Verify: `mvn clean test` succeeds

### Task 1: Implement Service Layer (7 minutes)

**Goal**: Create business logic layer

**Example Prompt**:
```
Following PLAN.md, implement TaskService in
src/main/java/com/workshop/taskapi/service/TaskService.java

The service should provide these operations:
- createTask(Task task) - Create new task
- getTaskById(Long id) - Get task by ID (throw TaskNotFoundException if not found)
- getAllTasks() - Get all tasks
- updateTask(Long id, Task task) - Update existing task
- deleteTask(Long id) - Delete task

Requirements:
- Use @Service annotation
- Constructor injection for TaskRepository (follow CLAUDE.md)
- Add @Transactional where appropriate
- Proper exception handling
- Follow CLAUDE.md service layer conventions
```

**What Should Be Created**:
```java
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        // Validation, business logic
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    // ... other methods
}
```

**Also Create Custom Exception**:
```
Create TaskNotFoundException in src/main/java/com/workshop/taskapi/exception/TaskNotFoundException.java
It should extend RuntimeException and include the task ID in the message.
```

**Verify**:
```bash
mvn clean compile
# Should compile successfully
```

**Success Check**:
- [ ] TaskService created with @Service
- [ ] All CRUD methods implemented
- [ ] Constructor injection used
- [ ] TaskNotFoundException created
- [ ] Code follows CLAUDE.md rules

### Task 2: Build REST Controller (8 minutes)

**Goal**: Create REST API endpoints

**Example Prompt**:
```
Following PLAN.md section "API Endpoints", implement TaskController in
src/main/java/com/workshop/taskapi/controller/TaskController.java

Create these endpoints exactly as specified in PLAN.md:
- POST /api/tasks - Create task (return 201 Created)
- GET /api/tasks - Get all tasks (return 200 OK)
- GET /api/tasks/{id} - Get task by ID (return 200 OK or 404)
- PUT /api/tasks/{id} - Update task (return 200 OK or 404)
- DELETE /api/tasks/{id} - Delete task (return 204 No Content)

Requirements:
- Use @RestController and @RequestMapping("/api/tasks")
- Proper HTTP status codes (check PLAN.md)
- @Valid for input validation
- Follow CLAUDE.md REST conventions
- Inject TaskService via constructor
```

**What Should Be Created**:
```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // ... other endpoints
}
```

**Verify**:
```bash
# Start the application
mvn spring-boot:run

# In another terminal, test the API
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Task","description":"Testing API","status":"PENDING"}'

curl http://localhost:8080/api/tasks
```

**Success Check**:
- [ ] TaskController created with all 5 endpoints
- [ ] Proper HTTP methods and status codes
- [ ] API responds to requests
- [ ] Validation works
- [ ] Follows CLAUDE.md conventions

### Task 3: Add Global Exception Handler (3 minutes)

**Goal**: Handle exceptions consistently

**Example Prompt**:
```
Create GlobalExceptionHandler in src/main/java/com/workshop/taskapi/exception/GlobalExceptionHandler.java

Use @ControllerAdvice to handle:
- TaskNotFoundException -> 404 Not Found
- MethodArgumentNotValidException -> 400 Bad Request (validation errors)
- General exceptions -> 500 Internal Server Error

Return structured error responses following CLAUDE.md error handling standards.
```

**Expected Result**:
```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(TaskNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            "Task not found",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Other exception handlers...
}
```

**Success Check**:
- [ ] GlobalExceptionHandler created
- [ ] 404 for TaskNotFoundException
- [ ] 400 for validation errors
- [ ] Error responses structured consistently

### Task 4: Write Integration Tests (5 minutes)

**Goal**: Test REST API end-to-end

**Example Prompt**:
```
Create TaskControllerIntegrationTest in
src/test/java/com/workshop/taskapi/TaskControllerIntegrationTest.java

Use @SpringBootTest and MockMvc or TestRestTemplate to test:
1. POST /api/tasks with valid data -> 201 Created
2. POST /api/tasks with invalid data -> 400 Bad Request
3. GET /api/tasks -> 200 OK with list
4. GET /api/tasks/{id} with valid ID -> 200 OK
5. GET /api/tasks/{id} with invalid ID -> 404 Not Found
6. PUT /api/tasks/{id} -> 200 OK
7. DELETE /api/tasks/{id} -> 204 No Content

Follow CLAUDE.md testing conventions and naming.
```

**Expected Tests**:
```java
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void shouldCreateTaskWhenValidDataProvided() throws Exception {
        String taskJson = "{\"title\":\"Test\",\"description\":\"Desc\",\"status\":\"PENDING\"}";

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value("Test"));
    }

    // More tests...
}
```

**Verify**:
```bash
mvn test
# All tests should pass
```

**Success Check**:
- [ ] Integration test class created
- [ ] Tests for all endpoints
- [ ] Tests for success and error scenarios
- [ ] All tests pass
- [ ] Follows naming convention

### Task 5: Create Custom Skill (5 minutes)

**Goal**: Automate endpoint creation

**Example Prompt**:
```
Create a custom Claude Code skill called "api-endpoint" that helps scaffold new REST endpoints.

The skill should be saved in .claude/skills/api-endpoint/ and should:
1. Prompt for endpoint details (name, HTTP method, path)
2. Create controller method with proper annotations
3. Create corresponding service method
4. Create basic integration test
5. Follow CLAUDE.md conventions automatically

Provide instructions for creating this skill.
```

**Skill Structure**:
```
.claude/skills/api-endpoint/
├── skill.md          # Instructions for Claude
└── README.md         # Documentation for developers
```

**skill.md example**:
````markdown
# API Endpoint Scaffolding Skill

When invoked with `/api-endpoint`, help the user create a new REST endpoint.

## Steps:
1. Ask for:
   - Feature name (e.g., "task assignment")
   - HTTP method (GET, POST, PUT, DELETE)
   - Path (e.g., "/api/tasks/{id}/assign")
   - Request/response details

2. Create controller method:
   - Proper HTTP method annotation
   - Path mapping
   - Request/response handling
   - Follow CLAUDE.md REST conventions

3. Create service method:
   - Business logic stub
   - Proper transaction handling

4. Create integration test:
   - Test happy path
   - Test error scenarios
   - Follow CLAUDE.md test naming

## Example:
User: `/api-endpoint` → Guide them through creating a complete endpoint
````

**To Test Your Skill**:
```
/api-endpoint

Then follow the prompts to create a sample endpoint
```

**Success Check**:
- [ ] Skill directory created
- [ ] skill.md with clear instructions
- [ ] README.md explaining the skill
- [ ] Skill can be invoked with `/api-endpoint`
- [ ] Skill generates code following CLAUDE.md

---

## Example Prompts for Common Tasks

### Creating a new endpoint:
```
Following PLAN.md API Endpoints section, add the GET /api/tasks/{id} endpoint
to TaskController. Ensure it returns 404 if task not found. Follow CLAUDE.md conventions.
```

### Adding validation:
```
Add validation to the Task entity following PLAN.md validation rules.
Use Bean Validation annotations. Follow CLAUDE.md validation standards.
```

### Fixing status codes:
```
Review TaskController and ensure all endpoints return the correct HTTP status codes
as specified in PLAN.md API Endpoints section and CLAUDE.md REST conventions.
```

---

## Troubleshooting

### Problem: Controller returns 404 for all requests
**Symptoms**: curl returns 404 for /api/tasks

**Solutions**:
1. Check @RestController annotation present
2. Verify @RequestMapping("/api/tasks") path
3. Check Spring Boot component scanning includes controller package
4. Restart application after changes

### Problem: Validation doesn't work
**Symptoms**: Invalid data accepted by API

**Solutions**:
1. Add `@Valid` annotation to `@RequestBody Task task`
2. Ensure validation annotations on Task entity
3. Add spring-boot-starter-validation dependency (should be included)
4. Check GlobalExceptionHandler handles MethodArgumentNotValidException

### Problem: Tests fail with 500 errors
**Symptoms**: Integration tests return 500 status

**Solutions**:
1. Check application logs for actual error
2. Verify H2 database configuration
3. Ensure test database is clean (@BeforeEach deleteAll)
4. Check service layer exception handling

### Problem: Skills don't work
**Symptoms**: `/api-endpoint` not recognized

**Solutions**:
1. Verify skill is in `.claude/skills/api-endpoint/` directory
2. Check skill.md file exists and has content
3. Restart Claude CLI if needed
4. Review skill documentation format

---

## Success Criteria

Before moving to Session 3, ensure:
- [ ] TaskService implemented with all CRUD methods
- [ ] TaskController has all 5 endpoints
- [ ] GlobalExceptionHandler handles exceptions
- [ ] Integration tests written and passing
- [ ] API works (tested with curl/Postman)
- [ ] Custom `/api-endpoint` skill created
- [ ] All code follows CLAUDE.md rules
- [ ] `mvn clean test` succeeds

---

## Time Check

| Task | Target Time | Running Total |
|------|-------------|---------------|
| Implement Service | 7 min | 7 min |
| Build Controller | 8 min | 15 min |
| Exception Handler | 3 min | 18 min |
| Integration Tests | 5 min | 23 min |
| Create Skill | 5 min | 28 min |

**Note**: Slight overtime is acceptable. Can reduce Task 5 if needed.

**If Behind**: Use checkpoint `session-2-complete` to catch up for Session 3.

---

## Key Takeaways

1. **Plan Reference**: Explicitly mention PLAN.md sections in prompts
2. **Rules Work**: CLAUDE.md enforces consistency automatically
3. **Layers Matter**: Controller → Service → Repository separation is clean
4. **Skills Save Time**: Custom skills automate repetitive tasks
5. **Test Integration**: Test full request/response cycle, not just units

---

## What's Next?

**Session 3 Preview**:
- Add filtering and search capabilities
- Use Explore agent to find patterns
- Use code-review agent for quality
- Use Plan agent to design next feature
- Learn when to use which agent

**Preparation**:
- Your REST API is now fully functional
- PLAN.md will guide the filter/search feature
- Agents will help with more complex tasks

**Ready to continue?** Proceed to Session 3!
