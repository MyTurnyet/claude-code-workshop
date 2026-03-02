# Session 1: Planning-Driven Development

**Duration**: 25 minutes
**Goal**: Create comprehensive planning documents and implement foundational code

---

## Learning Objectives

By the end of this session, you will:
- Understand the value of planning documents in development
- Create an effective PLAN.md that guides implementation
- Establish project rules with CLAUDE.md for consistency
- Implement a basic JPA entity following your plan
- Write your first tests

---

## Key Concepts

### PLAN.md - Your Source of Truth
- **Purpose**: Single source of truth for what you're building
- **Benefits**:
  - Reduces ambiguity in implementation
  - Provides reference for all team members
  - Guides Claude's code generation
  - Makes it easy to restart or onboard
- **Living Document**: Update as requirements evolve

### CLAUDE.md - Automatic Rule Enforcement
- **Purpose**: Define project-specific conventions
- **Benefits**:
  - Consistent code style across team
  - Automatic enforcement (no manual reviews needed)
  - Reduces cognitive load (don't rethink conventions)
  - Easy onboarding (rules are documented)

### Task-Based Development Workflow
1. **Plan**: Create/update PLAN.md
2. **Implement**: Reference plan in prompts
3. **Test**: Verify implementation matches plan
4. **Iterate**: Update plan if needed

---

## Step-by-Step Instructions

### Before You Start
- Ensure project compiles: `mvn clean compile`
- Verify basic test passes: `mvn test`
- Have IDE open and ready

### Task 1: Create PLAN.md (8 minutes)

**Goal**: Create a comprehensive project plan

**Example Prompt**:
```
Help me create a comprehensive PLAN.md for a Task Management REST API.

Include these sections:
1. Project Overview - what we're building and why
2. Features List - 4 features (CRUD, REST API, filtering, assignments)
3. API Endpoints - all REST endpoints with request/response examples
4. Data Model - Task entity with all fields and validation
5. Testing Strategy - what to test and how
6. Implementation Phases - breakdown by session

Use docs/PLAN-template.md as reference, but create a complete, detailed plan
that will guide implementation across all 4 workshop sessions.
```

**What to Include**:
- **Project Overview**: Purpose, goals, tech stack
- **Features**: 4 features (one per session)
  - Feature 1: Basic CRUD
  - Feature 2: REST API
  - Feature 3: Filtering/search
  - Feature 4: Assignments + MCP
- **API Endpoints**: All 5+ REST endpoints with examples
- **Data Model**: Task entity with fields like:
  - id, title, description, status, priority, dueDate
  - createdAt, updatedAt, assignee, tags
- **Testing Strategy**: Unit and integration test approach
- **Phases**: What to build in each session

**Success Check**:
- [ ] PLAN.md created in project root
- [ ] All sections filled with specific details
- [ ] Clear enough to guide implementation
- [ ] Reference docs/PLAN-complete.md if needed

### Task 2: Create CLAUDE.md (5 minutes)

**Goal**: Establish project rules and conventions

**Example Prompt**:
```
Create a CLAUDE.md rules file for this Spring Boot project.

Include rules for:
- Java code style (naming, formatting, organization)
- Spring Boot conventions (Controller/Service/Repository patterns)
- Testing requirements (JUnit 5, naming, coverage goals)
- Commit message format (conventional commits)
- Documentation standards (Javadoc requirements)

Use docs/CLAUDE-template.md as reference. These rules will be automatically
enforced throughout the workshop.
```

**What to Include**:
- **Code Style**: Naming, formatting, organization
- **Architecture**: Controller → Service → Repository layers
- **REST Conventions**: HTTP methods, status codes, paths
- **Testing**: Naming convention, coverage targets, AAA pattern
- **Git**: Commit message format
- **Documentation**: When to add Javadoc

**Success Check**:
- [ ] CLAUDE.md created in project root
- [ ] Clear, actionable rules defined
- [ ] Rules cover all major areas

### Task 3: Implement Task Entity (8 minutes)

**Goal**: Create the Task JPA entity following your plan

**Example Prompt**:
```
Following PLAN.md section "Data Model", implement the Task entity in
src/main/java/com/workshop/taskapi/model/Task.java

Requirements:
- Use JPA annotations (@Entity, @Id, @GeneratedValue)
- Include all fields from the data model section
- Add validation annotations (@NotBlank, @Size, etc.)
- Follow CLAUDE.md code style rules
- Include proper constructors (no-arg for JPA, all-args for convenience)
- Generate getters and setters
- Override equals/hashCode/toString
```

**What Should Be Created**:
```java
package com.workshop.taskapi.model;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String title;

    // ... other fields from your PLAN.md

    // Constructors, getters, setters, etc.
}
```

**Verify**:
```bash
mvn clean compile
# Should compile without errors
```

**Success Check**:
- [ ] Task.java created in correct package
- [ ] All fields from PLAN.md included
- [ ] JPA annotations present
- [ ] Validation annotations added
- [ ] Code compiles successfully

### Task 4: Create Repository (2 minutes)

**Goal**: Create repository interface for database access

**Example Prompt**:
```
Create TaskRepository interface in
src/main/java/com/workshop/taskapi/repository/TaskRepository.java

It should:
- Extend JpaRepository<Task, Long>
- Include any custom query methods mentioned in PLAN.md (if any)
- Follow CLAUDE.md conventions
```

**Expected Result**:
```java
package com.workshop.taskapi.repository;

import com.workshop.taskapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring Data JPA provides basic CRUD operations automatically
    // Add custom queries here in later sessions
}
```

**Success Check**:
- [ ] TaskRepository.java created
- [ ] Extends JpaRepository correctly
- [ ] Compiles without errors

### Task 5: Write Repository Tests (2 minutes)

**Goal**: Verify repository works correctly

**Example Prompt**:
```
Create TaskRepositoryTest in src/test/java/com/workshop/taskapi/TaskRepositoryTest.java

Tests should:
- Use @DataJpaTest annotation
- Test saving a task
- Test finding a task by ID
- Test findAll()
- Follow CLAUDE.md test naming conventions
```

**Expected Tests**:
```java
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    @Test
    void shouldSaveTaskSuccessfully() {
        // Arrange
        Task task = new Task("Test Task", "Description");

        // Act
        Task saved = repository.save(task);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("Test Task", saved.getTitle());
    }

    @Test
    void shouldFindTaskById() {
        // Arrange
        Task task = repository.save(new Task("Test", "Desc"));

        // Act
        Optional<Task> found = repository.findById(task.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Test", found.get().getTitle());
    }
}
```

**Verify**:
```bash
mvn test
# All tests should pass (including BasicSetupTest)
```

**Success Check**:
- [ ] Test class created
- [ ] At least 2 tests written
- [ ] All tests pass
- [ ] Tests follow naming convention

---

## Example Prompts for Common Issues

### If Claude doesn't follow PLAN.md:
```
Following the PLAN.md file in this project, specifically the "Data Model" section,
implement the Task entity exactly as specified. Reference PLAN.md explicitly.
```

### If code doesn't follow CLAUDE.md:
```
Implement this following the rules in CLAUDE.md, particularly the code style
and Spring Boot conventions sections.
```

### If stuck on what to include:
```
Review the docs/PLAN-complete.md example and help me create a similar plan
for my Task Management API. Be as detailed as the example.
```

---

## Troubleshooting

### Problem: Compilation errors in Task entity
**Symptoms**: `mvn compile` fails with import errors

**Solutions**:
1. Check that Spring Boot dependencies are in pom.xml
2. Verify JPA imports are correct:
   ```java
   import jakarta.persistence.*; // Note: jakarta, not javax in Spring Boot 3
   import jakarta.validation.constraints.*;
   ```
3. Ensure class is in correct package

### Problem: Tests fail with database errors
**Symptoms**: Tests fail with H2 connection errors

**Solutions**:
1. Check application.properties has H2 configuration
2. Verify @DataJpaTest annotation is present
3. Check that Task entity has proper @Entity annotation
4. Ensure test is in correct package

### Problem: PLAN.md is too vague
**Symptoms**: Unclear what to implement

**Solutions**:
1. Reference docs/PLAN-complete.md for detail level
2. Ask Claude to elaborate: "Make the API Endpoints section more specific with full request/response examples"
3. Add acceptance criteria for each feature

### Problem: CLAUDE.md rules not being followed
**Symptoms**: Generated code doesn't match style guide

**Solutions**:
1. Explicitly mention CLAUDE.md in prompts: "Follow CLAUDE.md rules"
2. Be specific: "Follow the Java naming conventions in CLAUDE.md"
3. Verify CLAUDE.md is in project root

---

## Success Criteria

Before moving to Session 2, ensure:
- [ ] PLAN.md exists and is comprehensive
- [ ] CLAUDE.md exists with clear rules
- [ ] Task entity implemented with all fields
- [ ] TaskRepository interface created
- [ ] Repository tests written and passing
- [ ] `mvn clean test` succeeds
- [ ] Code follows CLAUDE.md conventions
- [ ] You understand how PLAN.md guides development

---

## Time Check

| Task | Target Time | Running Total |
|------|-------------|---------------|
| Create PLAN.md | 8 min | 8 min |
| Create CLAUDE.md | 5 min | 13 min |
| Implement Task entity | 8 min | 21 min |
| Create Repository | 2 min | 23 min |
| Write tests | 2 min | 25 min |

**Buffer**: If ahead, review docs/PLAN-complete.md to see what a production-quality plan looks like.

**If Behind**: Use checkpoint branch `session-1-complete` to catch up for Session 2.

---

## Key Takeaways

1. **Planning First**: Always create plan before coding
2. **Reference Plans**: Mention PLAN.md explicitly in prompts
3. **Rules Help**: CLAUDE.md enforces consistency automatically
4. **Iterate**: Plans evolve - update them as you learn
5. **Test Early**: Write tests as you build, not after

---

## What's Next?

**Session 2 Preview**:
- Build REST API controllers
- Implement service layer
- Create integration tests
- Build your first custom skill

**Preparation**:
- Your PLAN.md will guide Session 2 implementation
- CLAUDE.md rules will be automatically enforced
- Task entity is the foundation for the API

**Ready to continue?** Proceed to Session 2!
