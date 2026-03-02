# API Endpoint Skill

Custom Claude Code skill for scaffolding REST API endpoints.

## Purpose

Automate the creation of new REST endpoints by generating:
- Controller methods with proper annotations
- Service methods with business logic
- Integration tests
- Following project conventions (CLAUDE.md)

## Usage

```
/api-endpoint
```

Then answer the prompts about your new endpoint:
- What it does
- HTTP method
- Path
- Request/response structure

## What Gets Created

1. **Controller Method**: In `TaskController.java`
   - HTTP method annotation
   - Path mapping
   - Parameter handling
   - Validation
   - Javadoc

2. **Service Method**: In `TaskService.java`
   - Business logic stub
   - Transaction management
   - Exception handling
   - Javadoc

3. **Integration Tests**: In `TaskControllerIntegrationTest.java`
   - Success scenario test
   - Error scenario tests (404, 400, etc.)
   - Follows naming conventions

## Example

Creating an endpoint to assign tasks:

```
User: /api-endpoint

Claude: What would you like the endpoint to do?
User: Assign a task to a user

Claude: What HTTP method? (GET, POST, PUT, DELETE)
User: POST

Claude: What path should it use?
User: /api/tasks/{id}/assign

[etc...]
```

Result: Complete endpoint implementation with tests!

## Benefits

- **Consistency**: All endpoints follow same pattern
- **Speed**: Create endpoints in seconds, not minutes
- **Quality**: Includes tests and documentation automatically
- **Learning**: See best practices in generated code

## Tips

- Be specific about your requirements
- Mention any special validation needs
- Think about error scenarios upfront
- Review generated code and customize as needed

## Created In

Session 2 of the Claude Code Workshop
