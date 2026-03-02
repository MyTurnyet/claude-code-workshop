# API Endpoint Scaffolding Skill

This skill helps scaffold new REST API endpoints with controller methods, service methods, and tests.

## When Invoked

When the user types `/api-endpoint`, guide them through creating a complete REST endpoint.

## Steps

### 1. Gather Requirements
Ask the user for:
- **Endpoint name** (e.g., "assign task", "get task summary")
- **HTTP method** (GET, POST, PUT, DELETE, PATCH)
- **Path** (e.g., "/api/tasks/{id}/assign")
- **Request body structure** (if POST/PUT/PATCH)
- **Response structure**
- **Query parameters** (if any)

### 2. Create Controller Method
Generate a controller method with:
- Appropriate HTTP method annotation (@GetMapping, @PostMapping, etc.)
- Path mapping
- Method parameters (@PathVariable, @RequestParam, @RequestBody)
- @Valid annotation for request bodies
- Proper return type (ResponseEntity<T>)
- Javadoc comments
- Follow CLAUDE.md conventions

Example:
```java
/**
 * Assign a task to a user.
 *
 * @param id the task ID
 * @param assigneeEmail the email of the assignee
 * @return the updated task
 */
@PostMapping("/{id}/assign")
public ResponseEntity<Task> assignTask(
    @PathVariable Long id,
    @RequestParam String assigneeEmail
) {
    Task updated = taskService.assignTask(id, assigneeEmail);
    return ResponseEntity.ok(updated);
}
```

### 3. Create Service Method
Generate a corresponding service method with:
- Business logic stub
- @Transactional if modifying data
- Proper exception handling
- Javadoc comments

Example:
```java
/**
 * Assign a task to a user.
 *
 * @param taskId the task ID
 * @param assigneeEmail the assignee's email
 * @return the updated task
 * @throws TaskNotFoundException if task not found
 */
@Transactional
public Task assignTask(Long taskId, String assigneeEmail) {
    Task task = getTaskById(taskId);
    task.setAssignee(assigneeEmail);
    return taskRepository.save(task);
}
```

### 4. Create Integration Test
Generate a test in TaskControllerIntegrationTest with:
- Test for successful operation
- Test for error scenarios (404, 400, etc.)
- Follow shouldDoSomethingWhenCondition naming convention
- Use MockMvc

Example:
```java
@Test
void shouldAssignTaskWhenValidEmailProvided() throws Exception {
    // Arrange
    Task task = taskRepository.save(new Task("Task", "Description"));

    // Act & Assert
    mockMvc.perform(post("/api/tasks/" + task.getId() + "/assign")
            .param("assigneeEmail", "user@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.assignee").value("user@example.com"));
}

@Test
void shouldReturnNotFoundWhenAssigningNonExistentTask() throws Exception {
    mockMvc.perform(post("/api/tasks/999/assign")
            .param("assigneeEmail", "user@example.com"))
        .andExpect(status().isNotFound());
}
```

### 5. Provide Usage Instructions
Tell the user:
- Where to add the controller method (TaskController)
- Where to add the service method (TaskService)
- Where to add the test (TaskControllerIntegrationTest)
- How to test the endpoint (curl example or Postman)

## Best Practices
- Always follow CLAUDE.md conventions
- Include proper validation
- Handle edge cases
- Write comprehensive tests
- Add clear Javadoc
- Use appropriate HTTP status codes

## Example Workflow

**User**: `/api-endpoint`

**Claude**:
> I'll help you create a new REST endpoint. Let me gather some information:
>
> 1. What would you like the endpoint to do? (e.g., "assign task to user", "get task statistics")
>
> [Wait for user response, then continue with remaining questions]
>
> [After gathering all info, generate the code following the steps above]
