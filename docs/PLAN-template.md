# Project Planning Template

Use this template to create your own PLAN.md for the Task Management API.

## Project Overview

**Purpose**: [Describe what this API does and why it exists]

**Goals**:
- [Primary goal]
- [Secondary goal]
- [Success metrics]

**Technical Stack**:
- Language: Java 17
- Framework: Spring Boot 3.x
- Database: H2 (in-memory)
- Build Tool: Maven
- Testing: JUnit 5

## Features List

Break down your application into manageable features:

### Feature 1: [Feature Name]
**Description**: [What this feature does]

**User Stories**:
- As a [user type], I want to [action] so that [benefit]

**Acceptance Criteria**:
- [ ] Criterion 1
- [ ] Criterion 2

### Feature 2: [Feature Name]
[Same structure as above]

### Feature 3: [Feature Name]
[Same structure as above]

### Feature 4: [Feature Name]
[Same structure as above]

## API Endpoints

Document all REST API endpoints:

### Endpoint 1: [Endpoint Name]
- **Method**: GET/POST/PUT/DELETE
- **Path**: `/api/...`
- **Description**: [What it does]
- **Request Body** (if applicable):
  ```json
  {
    "field": "value"
  }
  ```
- **Response**:
  - **Success (200/201)**:
    ```json
    {
      "id": 1,
      "field": "value"
    }
    ```
  - **Error (400/404/500)**:
    ```json
    {
      "error": "message"
    }
    ```
- **Query Parameters** (if applicable):
  - `param1`: description
  - `param2`: description

[Repeat for all endpoints]

## Data Model

### Entity: Task (or your main entity name)

**Fields**:
| Field Name | Type | Constraints | Description |
|------------|------|-------------|-------------|
| id | Long | Primary Key, Auto-generated | Unique identifier |
| [field] | [type] | [constraints] | [description] |

**Relationships**:
- [Describe any entity relationships]

**Validation Rules**:
- [Field] must be [constraint]
- [Field] must match [pattern]

**JPA Annotations**:
- @Entity
- @Table(name = "...")
- @Id, @GeneratedValue
- [Others as needed]

## Testing Strategy

### Unit Tests
**What to test**:
- Repository layer: CRUD operations
- Service layer: Business logic
- Individual component behavior

**Test Structure**:
- Use JUnit 5
- Follow naming convention: `shouldDoSomethingWhenCondition`
- Mock dependencies

### Integration Tests
**What to test**:
- Controller endpoints (full request/response cycle)
- Database interactions
- API contract compliance

**Test Structure**:
- Use @SpringBootTest
- Use TestRestTemplate or MockMvc
- Test realistic scenarios

### Coverage Goals
- Minimum: 70% code coverage
- Focus on business logic (service layer)
- All public API endpoints must be tested

## Implementation Phases

### Phase 1: Session 1
**Goal**: [What to accomplish]

**Tasks**:
- [ ] Task 1
- [ ] Task 2
- [ ] Task 3

**Deliverables**:
- [What should exist at end of session]

### Phase 2: Session 2
[Same structure]

### Phase 3: Session 3
[Same structure]

### Phase 4: Session 4
[Same structure]

## Technical Considerations

### Error Handling
- Use appropriate HTTP status codes
- Return meaningful error messages
- Handle validation errors
- Handle not found scenarios

### Security
- [Any security considerations]
- Input validation
- SQL injection prevention (via JPA)

### Performance
- Use appropriate indexing
- Consider caching (if needed)
- Optimize database queries

## Questions to Answer

As you fill out this template, consider:

1. **Features**: What features provide the most value?
2. **API Design**: Are endpoints RESTful and intuitive?
3. **Data Model**: Is the schema normalized? Flexible enough?
4. **Testing**: What scenarios need coverage?
5. **Implementation**: Can features be built incrementally?

## Tips for Creating Effective Plans

1. **Be Specific**: Vague plans lead to unclear implementation
2. **Think User-First**: Start with user stories, then technical details
3. **Keep It Living**: Update the plan as you learn
4. **Reference It Often**: Use plan to guide all implementation decisions
5. **Balance Detail**: Enough detail to guide, not so much it becomes rigid

---

**Ready to create your plan?** Prompt Claude:
```
Help me create a comprehensive PLAN.md for a Task Management REST API using this template.
Fill in all sections with specific details for managing tasks (create, read, update, delete,
search, assign, etc.). Make it detailed enough to guide implementation.
```
