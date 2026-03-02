# Session 3: Advanced Features with Agents

**Duration**: 25 minutes
**Goal**: Use specialized agents for complex tasks and implement advanced features

---

## Learning Objectives

By the end of this session, you will:
- Understand different agent types and their use cases
- Know when to use agents vs direct commands
- Use Explore agent to find patterns in code
- Use code-review agent to improve code quality
- Use Plan agent to design new features
- Incorporate agent feedback into implementation

---

## Key Concepts

### What Are Agents?
- **Specialized subagents** that work autonomously
- Each agent has specific capabilities and tools
- Agents can explore, plan, review, and more
- They work in parallel while you continue other tasks

### Agent Types

| Agent | Purpose | When to Use |
|-------|---------|-------------|
| **Explore** | Search codebase, find patterns | Learning existing code, finding examples |
| **Plan** | Design features, architecture | Planning new features, refactoring strategies |
| **code-review** | Analyze code quality, find bugs | After implementation, before commits |
| **test-runner** | Run and analyze tests | Continuous testing, debugging |
| **general-purpose** | Complex multi-step tasks | When task requires multiple operations |

### When to Use Agents

**Use Agents When**:
- Task is complex (multiple steps)
- Need deep code analysis
- Want parallel work
- Planning/designing architecture
- Need thorough code review

**Use Direct Commands When**:
- Task is simple and focused
- You know exactly what to do
- Quick edits or fixes
- Following existing patterns

---

## Step-by-Step Instructions

### Before You Start
- Session 2 complete (REST API working, tests passing)
- API endpoints functional
- PLAN.md and CLAUDE.md exist

### Task 1: Design Filter Feature with Plan Agent (5 minutes)

**Goal**: Use Plan agent to design filtering and search

**Example Prompt**:
```
Launch a Plan agent to design the task filtering and search feature for our API.

Context:
- We have a working REST API (see TaskController)
- PLAN.md describes Feature 3: filtering and search
- Need to filter by status and search by keywords

The agent should analyze our codebase and recommend:
1. Repository query methods needed
2. Controller changes (query parameters)
3. Service layer updates
4. Testing strategy

Let the agent explore the code and provide a detailed implementation plan.
```

**What the Agent Will Do**:
- Explore current codebase
- Read PLAN.md for requirements
- Analyze existing patterns
- Provide detailed recommendations

**Review Agent Output**:
- Read the agent's recommendations carefully
- Ask follow-up questions if unclear
- Note any suggested query methods
- Understand the testing approach

**Success Check**:
- [ ] Plan agent launched and completed
- [ ] Detailed recommendations received
- [ ] Query methods identified
- [ ] Implementation strategy clear

### Task 2: Implement Repository Query Methods (5 minutes)

**Goal**: Add filtering and search to repository

**Example Prompt**:
```
Following the Plan agent's recommendations and PLAN.md Feature 3,
add query methods to TaskRepository for:

1. Filter by status: findByStatus(TaskStatus status)
2. Search by keyword: findByTitleContainingOrDescriptionContaining(String keyword)
3. Combined filter and search (if recommended by agent)

Use Spring Data JPA query derivation or @Query annotation.
Follow CLAUDE.md conventions.
```

**What Should Be Added**:
```java
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String titleKeyword,
        String descriptionKeyword
    );

    // Or using @Query
    @Query("SELECT t FROM Task t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:search IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Task> findByStatusAndSearch(
        @Param("status") TaskStatus status,
        @Param("search") String search
    );
}
```

**Success Check**:
- [ ] Query methods added to repository
- [ ] Methods follow Spring Data JPA conventions
- [ ] Can handle null parameters (optional filtering)

### Task 3: Update Controller for Query Parameters (5 minutes)

**Goal**: Add filtering to GET /api/tasks endpoint

**Example Prompt**:
```
Following the Plan agent's recommendations, update the GET /api/tasks endpoint
in TaskController to support query parameters:

- ?status=PENDING (filter by status)
- ?search=keyword (search in title/description)
- Both parameters should be optional
- They should work together when both provided

Update TaskService as needed.
Follow CLAUDE.md REST conventions.
```

**What Should Be Updated**:
```java
// In TaskController
@GetMapping
public List<Task> getAllTasks(
    @RequestParam(required = false) TaskStatus status,
    @RequestParam(required = false) String search
) {
    return taskService.getAllTasks(status, search);
}

// In TaskService
public List<Task> getAllTasks(TaskStatus status, String search) {
    if (status == null && search == null) {
        return taskRepository.findAll();
    }
    return taskRepository.findByStatusAndSearch(status, search);
}
```

**Verify**:
```bash
# Test filtering
curl "http://localhost:8080/api/tasks?status=PENDING"

# Test search
curl "http://localhost:8080/api/tasks?search=important"

# Test combined
curl "http://localhost:8080/api/tasks?status=COMPLETED&search=bug"
```

**Success Check**:
- [ ] Controller accepts query parameters
- [ ] Service layer handles filtering logic
- [ ] All parameter combinations work
- [ ] Empty results handled correctly

### Task 4: Launch Code Review Agent (5 minutes)

**Goal**: Get expert review of your code

**Example Prompt**:
```
Launch a code-review agent to review the TaskService class.

Focus areas:
- Potential bugs or edge cases
- Performance issues
- Code quality and maintainability
- Best practices violations
- Opportunities for refactoring

After the review, summarize the top 3 priorities for improvement.
```

**Agent Will Analyze**:
- Logic errors
- Exception handling
- Performance bottlenecks
- Code smells
- Security issues
- Best practices

**Review Agent Feedback**:
- Read all findings carefully
- Prioritize by severity (critical vs nice-to-have)
- Understand the reasoning
- Plan which improvements to make

**Success Check**:
- [ ] code-review agent launched
- [ ] Detailed feedback received
- [ ] Findings categorized by priority
- [ ] Action items identified

### Task 5: Refactor Based on Feedback (3 minutes)

**Goal**: Improve code based on agent recommendations

**Example Prompt**:
```
Based on the code-review agent's feedback, implement the top 3 priority improvements
to TaskService.

Maintain all existing functionality and ensure tests still pass.
Follow CLAUDE.md conventions.
```

**Common Improvements**:
- Add null checks
- Improve error messages
- Extract repeated logic to methods
- Add logging
- Optimize queries
- Improve validation

**Verify**:
```bash
mvn clean test
# All tests should still pass after refactoring
```

**Success Check**:
- [ ] Improvements implemented
- [ ] Code quality improved
- [ ] Tests still pass
- [ ] No new bugs introduced

### Task 6: Design Feature 4 with Plan Agent (2 minutes)

**Goal**: Plan next session's feature

**Example Prompt**:
```
Launch a Plan agent to design Feature 4: Task assignments and tags.

Context from PLAN.md:
- Tasks should support assignment to users
- Tasks can have multiple tags
- Integration with Notion or Google Calendar (MCP)

The agent should provide:
1. Data model changes (entity updates, relationships)
2. New API endpoints needed
3. Service layer changes
4. MCP integration strategy
5. Testing approach

This plan will guide Session 4 implementation.
```

**Agent Output**:
- Detailed design for assignments
- MCP integration recommendations
- API endpoint specifications
- Implementation sequence

**Success Check**:
- [ ] Plan agent completed
- [ ] Feature 4 design documented
- [ ] Ready for Session 4 implementation

---

## Agent Usage Patterns

### Pattern 1: Explore → Implement
```
1. Use Explore agent to find similar patterns in codebase
2. Review findings
3. Implement following discovered patterns
```

### Pattern 2: Implement → Review → Refactor
```
1. Implement feature
2. Use code-review agent
3. Refactor based on feedback
```

### Pattern 3: Plan → Implement → Validate
```
1. Use Plan agent to design
2. Implement following plan
3. Use code-review agent to validate
```

---

## Example Prompts

### Launching an Explore Agent:
```
Launch an Explore agent to find examples of query parameter handling
in Spring Boot REST controllers. Look for patterns we can follow.
```

### Launching a Plan Agent:
```
Launch a Plan agent to design a comprehensive error handling strategy
for our API. Consider all error types and appropriate responses.
```

### Launching a Code Review Agent:
```
Launch a code-review agent to review all controller classes.
Focus on REST API best practices and potential security issues.
```

### Following Up with an Agent:
```
Ask the code-review agent to elaborate on the performance concern
it identified in the getAllTasks method.
```

---

## Troubleshooting

### Problem: Agent takes too long
**Symptoms**: Agent runs for several minutes

**Solutions**:
1. Be more specific in your prompt
2. Narrow the scope (specific file vs whole codebase)
3. Cancel and relaunch with clearer instructions
4. Some complex analysis takes time - be patient

### Problem: Agent feedback is unclear
**Symptoms**: Can't understand agent's recommendations

**Solutions**:
1. Ask follow-up questions
2. Request examples or code snippets
3. Ask agent to explain specific points
4. Reference PLAN.md or CLAUDE.md for context

### Problem: Implementing agent suggestions breaks tests
**Symptoms**: Tests fail after refactoring

**Solutions**:
1. Review what changed carefully
2. Use git diff to see exact changes
3. Revert and apply changes incrementally
4. Ask agent to suggest test updates
5. Use checkpoint branch if needed

### Problem: Agent recommendations conflict with PLAN.md
**Symptoms**: Agent suggests approach different from plan

**Solutions**:
1. Mention PLAN.md in agent prompt
2. Ask agent to reconcile with plan
3. Update PLAN.md if agent's approach is better
4. Prioritize plan unless agent identifies issues

---

## Success Criteria

Before moving to Session 4, ensure:
- [ ] Filter and search feature implemented
- [ ] Query parameters work correctly
- [ ] Code-review agent feedback reviewed
- [ ] Key improvements implemented
- [ ] All tests passing
- [ ] Plan for Feature 4 completed by agent
- [ ] Understand when to use each agent type

---

## Agent Decision Matrix

Use this to decide which agent to use:

**Explore Agent**:
- Finding examples in code
- Understanding existing patterns
- Discovering how something works
- Quick codebase searches

**Plan Agent**:
- Designing new features
- Architecture decisions
- Refactoring strategies
- Breaking down complex problems

**code-review Agent**:
- Quality assurance
- Finding bugs
- Performance analysis
- Best practices review

**General-Purpose Agent**:
- Complex multi-step tasks
- Tasks requiring multiple tools
- Research and implementation combined

---

## Time Check

| Task | Target Time | Running Total |
|------|-------------|---------------|
| Plan Feature with Agent | 5 min | 5 min |
| Implement Repository | 5 min | 10 min |
| Update Controller | 5 min | 15 min |
| Code Review Agent | 5 min | 20 min |
| Refactor | 3 min | 23 min |
| Plan Feature 4 | 2 min | 25 min |

**If Behind**: Skip Task 5 (refactoring) if needed, or use checkpoint `session-3-complete`.

---

## Key Takeaways

1. **Agents Are Powerful**: Use them for complex tasks
2. **Choose Wisely**: Different agents for different purposes
3. **Review Carefully**: Agent output needs human judgment
4. **Iterate**: Agents help refine and improve
5. **Combine**: Use multiple agents for comprehensive work

---

## What's Next?

**Session 4 Preview**:
- Implement task assignments
- Configure MCP integration (Notion or Calendar)
- Complete end-to-end workflow
- Demonstrate full Claude Code capabilities

**Preparation**:
- Feature 4 design from Plan agent guides implementation
- MCP will connect your API to external services
- Final session brings everything together

**Ready for the finale?** Proceed to Session 4!
