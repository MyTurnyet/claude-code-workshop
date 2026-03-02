# Project Rules - CLAUDE.md

Project-specific rules enforced automatically by Claude Code.

## Code Style

### Naming Conventions
- Classes: PascalCase
- Methods/Variables: camelCase
- Constants: UPPER_SNAKE_CASE
- Packages: lowercase

### Formatting
- Indentation: 4 spaces
- Line length: 120 characters max
- Braces: K&R style (opening on same line)
- One blank line between methods

## Spring Boot Architecture

### Layers
- **Controller**: HTTP handling, validation, delegate to service
- **Service**: Business logic, transactions
- **Repository**: Data access only

### REST Conventions
- Base path: `/api`
- Resource names: plural (e.g., `/api/tasks`)
- POST → 201 Created
- GET → 200 OK
- PUT → 200 OK
- DELETE → 204 No Content
- Not found → 404
- Validation error → 400

### Dependency Injection
- Use constructor injection
- Mark dependencies as final
- Avoid field injection

## Testing

### Naming
Format: `shouldDoSomethingWhenCondition`

### Structure
Follow AAA (Arrange-Act-Assert) pattern

### Coverage
- Overall: 80%+
- Service layer: 90%+
- Controller: 85%+

### Types
- Unit tests: Single class, mock dependencies
- Integration tests: Full Spring context

## Documentation

### Javadoc
- All public classes and methods
- Describe parameters and return values
- Mention exceptions thrown

### Comments
- Explain "why", not "what"
- Update when code changes
- No commented-out code

## Git Commits

### Format
`<type>(<scope>): <description>`

### Types
- feat: New feature
- fix: Bug fix
- refactor: Code improvement
- test: Test changes
- docs: Documentation

### Examples
- `feat(controller): add task filtering endpoint`
- `fix(service): handle null in updateTask`
- `test(repository): add search tests`

## Validation

### Input Validation
- Use Bean Validation annotations
- Validate at controller layer with @Valid
- Clear error messages

### Entity Validation
- @NotNull, @NotBlank for required fields
- @Size for length constraints
- @Email for email fields

## Error Handling

### Exceptions
- Custom exceptions for business errors
- RuntimeException for programming errors
- Use @ControllerAdvice for global handling

### Error Response Format
```json
{
  "error": "Error message",
  "details": "Additional info",
  "timestamp": "2024-03-01T10:00:00"
}
```

## Security

- Validate all input
- Use parameterized queries (JPA handles this)
- Don't expose internal details in errors
- No sensitive data in logs

## Performance

- Use appropriate indexes
- Avoid N+1 queries
- Consider pagination for large results
- Cache when beneficial
