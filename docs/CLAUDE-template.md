# Project Rules - CLAUDE.md Template

This file defines project-specific rules that Claude Code will enforce automatically throughout development. Customize these rules for your project.

---

## Code Style

### Java Naming Conventions
- **Classes**: PascalCase (e.g., `TaskController`, `TaskService`)
- **Methods**: camelCase (e.g., `createTask`, `findTaskById`)
- **Variables**: camelCase (e.g., `taskId`, `taskList`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_TITLE_LENGTH`, `DEFAULT_STATUS`)
- **Packages**: lowercase (e.g., `com.workshop.taskapi.controller`)

### Formatting
- **Indentation**: 4 spaces (no tabs)
- **Line Length**: Maximum 120 characters
- **Braces**: Opening brace on same line (K&R style)
  ```java
  public void method() {
      // code here
  }
  ```
- **Blank Lines**: One blank line between methods
- **Imports**: Organize and remove unused imports
- **No Wildcard Imports**: Use specific imports, not `import java.util.*`

### Code Organization
- One class per file
- Public classes first, private classes last
- Fields before methods
- Constructors before methods
- Public methods before private methods
- Group related methods together

---

## Spring Boot Conventions

### Architecture Layers
Follow standard Spring Boot layered architecture:

```
Controller Layer → Service Layer → Repository Layer → Database
```

- **Controllers** (`@RestController`):
  - Handle HTTP requests/responses
  - Validate input (use `@Valid`)
  - Delegate business logic to services
  - Return DTOs, not entities (in production scenarios)
  - Package: `com.workshop.taskapi.controller`

- **Services** (`@Service`):
  - Contain business logic
  - Transactional boundaries (`@Transactional`)
  - Coordinate between multiple repositories
  - Throw custom exceptions
  - Package: `com.workshop.taskapi.service`

- **Repositories** (`@Repository` or JPA interfaces):
  - Data access only
  - Extend `JpaRepository<Entity, ID>`
  - Custom queries with `@Query` or method naming
  - Package: `com.workshop.taskapi.repository`

### REST API Conventions
- **Base Path**: All endpoints under `/api`
- **Resource Naming**: Plural nouns (e.g., `/api/tasks`, not `/api/task`)
- **HTTP Methods**:
  - `POST` - Create new resource (return 201 Created)
  - `GET` - Retrieve resource(s) (return 200 OK)
  - `PUT` - Update entire resource (return 200 OK)
  - `PATCH` - Partial update (bonus feature)
  - `DELETE` - Remove resource (return 204 No Content)
- **Status Codes**:
  - 200 OK - Successful GET/PUT
  - 201 Created - Successful POST
  - 204 No Content - Successful DELETE
  - 400 Bad Request - Validation error
  - 404 Not Found - Resource not found
  - 500 Internal Server Error - Server error

### Dependency Injection
- Use constructor injection (preferred over field injection)
  ```java
  @Service
  public class TaskService {
      private final TaskRepository repository;

      public TaskService(TaskRepository repository) {
          this.repository = repository;
      }
  }
  ```
- Mark dependencies as `final`
- Use Lombok `@RequiredArgsConstructor` if available

### Exception Handling
- Use `@ControllerAdvice` for global exception handling
- Create custom exceptions: `TaskNotFoundException`, `TaskValidationException`
- Return structured error responses:
  ```java
  {
      "error": "Task not found",
      "taskId": 123,
      "timestamp": "2024-03-01T10:00:00"
  }
  ```

---

## Testing Requirements

### Test Framework
- Use JUnit 5 (Jupiter)
- Use Mockito for mocking
- Use AssertJ or built-in assertions

### Test Naming Convention
Format: `shouldDoSomethingWhenCondition`

Examples:
- `shouldReturnTaskWhenIdExists()`
- `shouldThrowExceptionWhenIdNotFound()`
- `shouldCreateTaskWhenValidDataProvided()`

### Test Structure
Follow AAA (Arrange-Act-Assert) pattern:
```java
@Test
void shouldReturnTaskWhenIdExists() {
    // Arrange
    Task task = new Task("Test Task");
    when(repository.findById(1L)).thenReturn(Optional.of(task));

    // Act
    Task result = service.getTaskById(1L);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getTitle()).isEqualTo("Test Task");
}
```

### Test Coverage Requirements
- **Minimum Overall Coverage**: 70%
- **Service Layer**: 90%+ (contains business logic)
- **Controller Layer**: 80%+ (via integration tests)
- **Repository Layer**: 70%+ (mostly integration tests)
- All public methods must have tests
- Test both happy paths and error scenarios

### Test Types
- **Unit Tests**: Test single class in isolation (use mocks)
  - Annotate with `@ExtendWith(MockitoExtension.class)`
  - Mock dependencies

- **Integration Tests**: Test multiple components together
  - Annotate with `@SpringBootTest` or `@DataJpaTest`
  - Use real Spring context
  - Test database interactions
  - Test REST endpoints end-to-end

### Test Organization
- Test classes mirror source structure
- Test class name: `{ClassName}Test`
- Integration tests can be in separate package or suffix: `{ClassName}IntegrationTest`

---

## Documentation Standards

### Javadoc Requirements
Add Javadoc for:
- All public classes (describe purpose)
- All public methods (describe what it does, parameters, returns, exceptions)
- Complex private methods (if logic is non-obvious)

**Format**:
```java
/**
 * Creates a new task with the provided details.
 *
 * @param task the task to create (must not be null)
 * @return the created task with generated ID
 * @throws TaskValidationException if task data is invalid
 */
public Task createTask(Task task) {
    // implementation
}
```

### Comments
- Write self-documenting code (clear names, simple logic)
- Add comments only when code intent is not obvious
- Explain "why", not "what" (code shows what it does)
- Update comments when code changes
- No commented-out code (use git history instead)

### README and Docs
- Keep README.md updated with setup instructions
- Document API endpoints (or use OpenAPI/Swagger)
- Update PLAN.md when requirements change

---

## Git and Version Control

### Commit Message Format
Follow Conventional Commits specification:

**Format**: `<type>(<scope>): <description>`

**Types**:
- `feat`: New feature
- `fix`: Bug fix
- `refactor`: Code refactoring (no functional change)
- `test`: Adding or updating tests
- `docs`: Documentation changes
- `chore`: Maintenance tasks

**Examples**:
```
feat(controller): add task filtering endpoint
fix(service): correct null handling in updateTask
test(repository): add tests for search functionality
refactor(service): simplify task creation logic
docs(readme): update setup instructions
```

### Commit Guidelines
- One logical change per commit
- Write clear, descriptive messages
- Reference issue numbers if applicable (e.g., `fixes #123`)
- Keep commits focused and atomic

### Branching
- `main`: Stable, working code
- Feature branches: `feature/task-filtering`
- Bug fixes: `fix/null-pointer-in-service`
- Merge via pull requests with code review

---

## Validation Rules

### Input Validation
- Validate at controller layer using `@Valid`
- Use Bean Validation annotations:
  - `@NotNull`, `@NotBlank`, `@NotEmpty`
  - `@Size(min, max)`
  - `@Email`, `@Pattern`
  - `@Min`, `@Max`
  - `@Future`, `@Past`

### Entity Validation
Apply validation annotations to entity fields:
```java
@NotBlank(message = "Title is required")
@Size(max = 200, message = "Title must not exceed 200 characters")
private String title;
```

### Custom Validation
- Create custom validators for complex rules
- Implement `ConstraintValidator` interface
- Use `@Constraint` annotation

---

## Error Handling Standards

### Exception Strategy
- Use checked exceptions sparingly (mainly for business logic errors)
- Use unchecked exceptions for programming errors
- Create custom exceptions extending `RuntimeException`

### Custom Exceptions
```java
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }
}
```

### Global Exception Handling
Use `@ControllerAdvice` to handle exceptions globally:
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(TaskNotFoundException ex) {
        // Return 404 with error details
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        // Return 400 with validation errors
    }
}
```

---

## Security Guidelines

### Input Sanitization
- Never trust user input
- Validate all incoming data
- Use parameterized queries (JPA handles this)
- Avoid string concatenation in queries

### Data Exposure
- Don't expose internal IDs unnecessarily
- Don't return full stack traces to clients
- Sanitize error messages (no sensitive info)

### Dependencies
- Keep dependencies updated
- Review security advisories
- Use Maven dependency:check plugin

---

## Performance Considerations

### Database Queries
- Use appropriate indexes
- Avoid N+1 query problems (use `@EntityGraph` or JOIN FETCH)
- Consider pagination for large result sets
- Use database-specific optimizations when needed

### Caching
- Cache frequently accessed data (optional)
- Use `@Cacheable` for appropriate methods
- Clear cache on updates

---

## Additional Rules

### Logging
- Use SLF4J with Logback (Spring Boot default)
- Log levels:
  - `ERROR`: Something failed
  - `WARN`: Something unexpected but handled
  - `INFO`: Important business events
  - `DEBUG`: Detailed debugging info
- Don't log sensitive data (passwords, tokens, PII)

### Configuration
- Externalize configuration (application.properties)
- Use profiles for different environments (dev, prod)
- Don't hardcode values
- Use `@Value` or `@ConfigurationProperties` for injection

---

## Enforcement

Claude Code will automatically:
- Follow these conventions in generated code
- Remind about rules when they might be violated
- Suggest improvements based on these guidelines
- Apply rules consistently across sessions

## Customization

Feel free to modify these rules for your team/project:
- Add company-specific conventions
- Adjust coverage thresholds
- Include team preferences
- Add project-specific patterns

---

**To use these rules**: Save this as `CLAUDE.md` in your project root. Claude Code will reference it automatically during development.

**Prompt to create your own**:
```
Create a CLAUDE.md rules file for this Spring Boot project using the template.
Customize it with our preferences:
- [Add your specific preferences]
- [Team conventions]
- [Project-specific rules]
```
