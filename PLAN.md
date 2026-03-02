# Task Management API - Project Plan

A RESTful API for managing tasks with CRUD operations, filtering, search, and external service integration.

## Project Overview

**Purpose**: Build a production-ready REST API for managing tasks, demonstrating Claude Code's task-based development approach.

**Goals**:
- Create fully functional task management system
- Demonstrate RESTful API best practices
- Integrate with external services via MCP
- Follow test-driven development
- Maintain high code quality

**Technical Stack**:
- Java 17
- Spring Boot 3.2.3
- H2 Database (in-memory)
- Maven
- JUnit 5

## Features

### Feature 1: Basic Task CRUD (Session 1)
- Create tasks with title, description, status
- Read all tasks and individual tasks
- Update task details
- Delete tasks
- Basic validation

### Feature 2: REST API (Session 2)
- Complete REST endpoints
- Proper HTTP methods and status codes
- Service layer for business logic
- Global exception handling
- Integration tests

### Feature 3: Filtering & Search (Session 3)
- Filter tasks by status
- Search tasks by keyword
- Combine filters
- Performance optimization

### Feature 4: Assignments & MCP Integration (Session 4)
- Assign tasks to users
- Add tags to tasks
- Integrate with Notion or Google Calendar
- External sync functionality

## API Endpoints

### POST /api/tasks
Create a new task
- Request: `{"title": "...", "description": "...", "status": "PENDING"}`
- Response: 201 Created with task object

### GET /api/tasks
Get all tasks (with optional filtering)
- Query params: `?status=PENDING&search=keyword`
- Response: 200 OK with array of tasks

### GET /api/tasks/{id}
Get task by ID
- Response: 200 OK with task, or 404 Not Found

### PUT /api/tasks/{id}
Update task
- Request: Task object with updates
- Response: 200 OK with updated task, or 404 Not Found

### DELETE /api/tasks/{id}
Delete task
- Response: 204 No Content, or 404 Not Found

## Data Model

### Task Entity
- id: Long (auto-generated)
- title: String (required, max 200)
- description: String (max 2000)
- status: TaskStatus enum (PENDING, IN_PROGRESS, COMPLETED)
- priority: Priority enum (LOW, MEDIUM, HIGH)
- dueDate: LocalDate
- assignee: String (email)
- tags: Set<String>
- createdAt: LocalDateTime (auto)
- updatedAt: LocalDateTime (auto)

## Testing Strategy

### Unit Tests
- Repository: CRUD operations
- Service: Business logic, validation
- 90%+ coverage for service layer

### Integration Tests
- Controller: Full HTTP request/response
- End-to-end scenarios
- 85%+ coverage for controller layer

## Implementation Phases

### Session 1: Foundation
- PLAN.md and CLAUDE.md
- Task entity with JPA
- TaskRepository
- Basic tests

### Session 2: REST API
- TaskService
- TaskController
- Exception handling
- Integration tests
- Custom skill

### Session 3: Advanced Features
- Query methods for filtering
- Search implementation
- Code review and refactoring

### Session 4: Integration
- Assignment fields
- MCP integration
- Complete workflow
