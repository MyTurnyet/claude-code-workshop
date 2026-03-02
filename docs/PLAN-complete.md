# Task Management API - Complete Project Plan

A comprehensive plan for building a RESTful Task Management API with Spring Boot.

## Project Overview

**Purpose**: Build a production-ready REST API for managing tasks with support for CRUD operations, filtering, search, and external service integration.

**Goals**:
- Create a fully functional task management system
- Demonstrate RESTful API best practices
- Integrate with external services (Notion or Google Calendar)
- Provide comprehensive test coverage
- Follow task-based development methodology

**Technical Stack**:
- **Language**: Java 17
- **Framework**: Spring Boot 3.2.3
- **Database**: H2 (in-memory for development)
- **Build Tool**: Maven 3.6+
- **Testing**: JUnit 5, Spring Boot Test
- **Documentation**: OpenAPI/Swagger (bonus)

## Features List

### Feature 1: Basic Task CRUD Operations
**Description**: Core functionality to create, read, update, and delete tasks.

**User Stories**:
- As a user, I want to create tasks with title and description
- As a user, I want to view all my tasks
- As a user, I want to update task details
- As a user, I want to delete completed tasks

**Acceptance Criteria**:
- [ ] Tasks can be created with required fields
- [ ] All tasks can be retrieved
- [ ] Individual tasks can be retrieved by ID
- [ ] Tasks can be updated
- [ ] Tasks can be deleted
- [ ] Proper validation on all operations
- [ ] Appropriate error handling for invalid operations

### Feature 2: RESTful API with Full HTTP Semantics
**Description**: Professional REST API following HTTP standards and best practices.

**User Stories**:
- As a developer, I want consistent RESTful endpoints
- As a client application, I need proper HTTP status codes
- As an API consumer, I want clear error messages

**Acceptance Criteria**:
- [ ] Endpoints follow REST conventions
- [ ] Correct HTTP methods (POST, GET, PUT, DELETE)
- [ ] Proper status codes (200, 201, 204, 400, 404, 500)
- [ ] JSON request/response format
- [ ] Validation errors return 400 with details
- [ ] Not found scenarios return 404

### Feature 3: Task Filtering and Search
**Description**: Advanced querying capabilities to filter and search tasks.

**User Stories**:
- As a user, I want to filter tasks by status (pending/completed)
- As a user, I want to search tasks by keywords in title or description
- As a user, I want to combine filters for precise queries

**Acceptance Criteria**:
- [ ] Can filter by status via query parameter
- [ ] Can search by keyword via query parameter
- [ ] Filters can be combined
- [ ] Empty results return empty array (not error)
- [ ] Query parameters are case-insensitive
- [ ] Search works across title and description

### Feature 4: Task Assignments and External Integration
**Description**: Assign tasks and sync with external services (Notion or Google Calendar).

**User Stories**:
- As a user, I want to assign tasks to team members
- As a user, I want tasks synced to Notion for documentation
- As a user, I want task due dates to appear in my calendar

**Acceptance Criteria**:
- [ ] Tasks can be assigned to users/team members
- [ ] Tasks can have tags for categorization
- [ ] Integration with Notion MCP for syncing tasks
- [ ] OR integration with Google Calendar for due dates
- [ ] Sync happens automatically on task creation/update
- [ ] Error handling for external service failures

## API Endpoints

### 1. Create Task
- **Method**: POST
- **Path**: `/api/tasks`
- **Description**: Create a new task
- **Request Body**:
  ```json
  {
    "title": "Implement user authentication",
    "description": "Add JWT-based authentication to the API",
    "status": "PENDING",
    "priority": "HIGH",
    "dueDate": "2024-12-31"
  }
  ```
- **Response**:
  - **Success (201 Created)**:
    ```json
    {
      "id": 1,
      "title": "Implement user authentication",
      "description": "Add JWT-based authentication to the API",
      "status": "PENDING",
      "priority": "HIGH",
      "dueDate": "2024-12-31",
      "createdAt": "2024-03-01T10:00:00",
      "updatedAt": "2024-03-01T10:00:00"
    }
    ```
  - **Error (400 Bad Request)**:
    ```json
    {
      "error": "Validation failed",
      "details": [
        "Title is required",
        "Title must be between 1 and 200 characters"
      ]
    }
    ```

### 2. Get All Tasks
- **Method**: GET
- **Path**: `/api/tasks`
- **Description**: Retrieve all tasks with optional filtering
- **Query Parameters**:
  - `status`: Filter by status (PENDING, IN_PROGRESS, COMPLETED)
  - `search`: Search in title and description
  - `priority`: Filter by priority (LOW, MEDIUM, HIGH)
- **Response**:
  - **Success (200 OK)**:
    ```json
    [
      {
        "id": 1,
        "title": "Task 1",
        "status": "PENDING",
        ...
      },
      {
        "id": 2,
        "title": "Task 2",
        "status": "COMPLETED",
        ...
      }
    ]
    ```

### 3. Get Task by ID
- **Method**: GET
- **Path**: `/api/tasks/{id}`
- **Description**: Retrieve a specific task
- **Path Parameters**:
  - `id`: Task ID (Long)
- **Response**:
  - **Success (200 OK)**: Single task object
  - **Error (404 Not Found)**:
    ```json
    {
      "error": "Task not found",
      "taskId": 999
    }
    ```

### 4. Update Task
- **Method**: PUT
- **Path**: `/api/tasks/{id}`
- **Description**: Update an existing task
- **Request Body**: Same as create (all fields optional)
- **Response**:
  - **Success (200 OK)**: Updated task object
  - **Error (404 Not Found)**: If task doesn't exist
  - **Error (400 Bad Request)**: If validation fails

### 5. Delete Task
- **Method**: DELETE
- **Path**: `/api/tasks/{id}`
- **Description**: Delete a task
- **Response**:
  - **Success (204 No Content)**: Empty response
  - **Error (404 Not Found)**: If task doesn't exist

### 6. Assign Task (Feature 4)
- **Method**: POST
- **Path**: `/api/tasks/{id}/assign`
- **Description**: Assign task to user/team member
- **Request Body**:
  ```json
  {
    "assignee": "john.doe@example.com",
    "notes": "Please complete by end of week"
  }
  ```
- **Response**: Updated task with assignment info

## Data Model

### Entity: Task

**Fields**:
| Field Name | Type | Constraints | Description |
|------------|------|-------------|-------------|
| id | Long | @Id, @GeneratedValue | Unique identifier |
| title | String | @NotBlank, @Size(max=200) | Task title |
| description | String | @Size(max=2000) | Detailed description |
| status | Enum | @Enumerated, @NotNull | PENDING, IN_PROGRESS, COMPLETED |
| priority | Enum | @Enumerated | LOW, MEDIUM, HIGH |
| dueDate | LocalDate | @Future (for new tasks) | Target completion date |
| assignee | String | @Email | Assigned user email |
| tags | Set<String> | @ElementCollection | Categorization tags |
| createdAt | LocalDateTime | Auto-generated | Creation timestamp |
| updatedAt | LocalDateTime | Auto-updated | Last update timestamp |

**Enums**:
```java
public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}

public enum Priority {
    LOW,
    MEDIUM,
    HIGH
}
```

**JPA Annotations**:
```java
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private TaskStatus status = TaskStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    private LocalDate dueDate;

    @Email(message = "Assignee must be a valid email")
    private String assignee;

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Getters, setters, constructors
}
```

**Validation Rules**:
- Title is required and max 200 characters
- Description is optional, max 2000 characters
- Status defaults to PENDING if not specified
- Priority defaults to MEDIUM if not specified
- Due date must be in the future for new tasks
- Assignee must be valid email format
- Tags are optional

## Testing Strategy

### Unit Tests

**Repository Layer**:
```java
@DataJpaTest
class TaskRepositoryTest {
    // Test save operation
    // Test findById
    // Test findAll
    // Test findByStatus (custom query)
    // Test findByTitleContainingOrDescriptionContaining (search)
    // Test delete operation
}
```

**Service Layer**:
```java
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    // Test createTask with valid data
    // Test createTask with invalid data (throws exception)
    // Test getTaskById - found scenario
    // Test getTaskById - not found scenario
    // Test updateTask
    // Test deleteTask
    // Test getAllTasks
    // Test filter and search functionality
}
```

**Coverage Goals**:
- Repository: 80%+ (mainly integration tests)
- Service: 90%+ (business logic critical)
- Controller: 85%+ (via integration tests)
- Overall: Minimum 80%

### Integration Tests

**Controller Layer**:
```java
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    // Test POST /api/tasks - success
    // Test POST /api/tasks - validation error
    // Test GET /api/tasks - all tasks
    // Test GET /api/tasks?status=PENDING - filtered
    // Test GET /api/tasks?search=keyword - search
    // Test GET /api/tasks/{id} - found
    // Test GET /api/tasks/{id} - not found
    // Test PUT /api/tasks/{id} - update success
    // Test PUT /api/tasks/{id} - not found
    // Test DELETE /api/tasks/{id} - success
    // Test DELETE /api/tasks/{id} - not found
}
```

**Testing Principles**:
- Test happy paths and error scenarios
- Test boundary conditions
- Verify HTTP status codes
- Validate response structure
- Test query parameter combinations
- Mock external dependencies (MCP services)

## Implementation Phases

### Phase 1: Session 1 - Foundation (25 minutes)
**Goal**: Establish planning foundation and implement basic entity

**Tasks**:
- [ ] Create this PLAN.md document
- [ ] Create CLAUDE.md with project rules
- [ ] Implement Task entity with all fields
- [ ] Create TaskRepository interface
- [ ] Write basic repository tests
- [ ] Verify compilation and tests pass

**Deliverables**:
- Complete planning documents
- Working Task entity with JPA
- Repository layer functioning
- Tests passing

### Phase 2: Session 2 - REST API (25 minutes)
**Goal**: Build complete REST API with all CRUD endpoints

**Tasks**:
- [ ] Implement TaskService with business logic
- [ ] Create TaskController with 5 endpoints
- [ ] Add proper exception handling
- [ ] Write integration tests for all endpoints
- [ ] Create custom `/api-endpoint` skill
- [ ] Verify API works with curl/Postman

**Deliverables**:
- Full REST API operational
- Integration tests passing
- Custom skill created
- API testable via HTTP

### Phase 3: Session 3 - Advanced Features (25 minutes)
**Goal**: Add filtering/search and use agents for code improvement

**Tasks**:
- [ ] Use Plan agent to design filter/search feature
- [ ] Add custom query methods to repository
- [ ] Update controller with query parameters
- [ ] Use code-review agent on service layer
- [ ] Refactor based on agent feedback
- [ ] Use Plan agent for Feature 4 design
- [ ] Write tests for new functionality

**Deliverables**:
- Filter and search working
- Code reviewed and improved
- Plan for next feature ready
- Tests passing

### Phase 4: Session 4 - Integration (30 minutes)
**Goal**: Add assignments and integrate with external service

**Tasks**:
- [ ] Choose MCP integration (Notion or Calendar)
- [ ] Configure MCP server
- [ ] Update Task entity for assignments
- [ ] Create assignment endpoints
- [ ] Implement MCP integration service
- [ ] Test end-to-end workflow
- [ ] Document complete workflow

**Deliverables**:
- Assignments feature complete
- MCP integration working
- All features tested
- Workshop complete!

## Technical Considerations

### Error Handling Strategy
- Use @ControllerAdvice for global exception handling
- Create custom exceptions: TaskNotFoundException, TaskValidationException
- Return consistent error response format
- Log errors appropriately
- Don't expose internal details in error messages

### Validation Approach
- Use Bean Validation (JSR-380) annotations
- Validate at controller layer (@Valid annotation)
- Custom validators for complex rules
- Clear validation messages for users

### Service Layer Design
- Single Responsibility Principle
- Transactional boundaries (@Transactional)
- Business logic isolated from controllers
- Dependency injection for repositories

### Repository Patterns
- Use Spring Data JPA
- Custom queries with @Query or method naming
- Avoid N+1 query problems
- Consider pagination for large datasets

### MCP Integration
- Async processing for external calls (optional)
- Retry logic for transient failures
- Circuit breaker pattern (if time permits)
- Graceful degradation if MCP unavailable

## Success Metrics

**Functionality**:
- [ ] All 5 CRUD endpoints working
- [ ] Filter and search operational
- [ ] Assignment feature complete
- [ ] MCP integration successful

**Code Quality**:
- [ ] Follows CLAUDE.md rules consistently
- [ ] 80%+ test coverage
- [ ] No critical bugs
- [ ] Clean, readable code

**Learning Outcomes**:
- [ ] Understand task-based development
- [ ] Can create effective plans
- [ ] Know when to use agents
- [ ] Can integrate MCP services

---

## How to Use This Plan

**During Implementation**:
1. Reference this plan in every prompt: "Following PLAN.md, implement..."
2. Check acceptance criteria as you complete features
3. Update plan if requirements change
4. Use plan sections to structure work

**With Claude**:
```
"Following PLAN.md section 'Data Model', implement the Task entity with all
specified fields, validation rules, and JPA annotations."
```

```
"According to PLAN.md 'API Endpoints', create the TaskController with all 5
REST endpoints using the exact request/response formats specified."
```

**Benefits**:
- Consistent implementation across sessions
- Clear acceptance criteria
- Reduced ambiguity in prompts
- Reference for testing scenarios

---

**This plan is a living document** - update it as you learn and requirements evolve!
