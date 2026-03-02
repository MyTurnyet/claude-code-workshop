# Checkpoint Branches Guide

This workshop includes checkpoint branches so participants can catch up if they fall behind or skip ahead to any session.

## Available Branches

### `main` (Starter Code)
**What's included:**
- Complete documentation and templates
- Empty Spring Boot project structure
- One passing test (BasicSetupTest)
- Ready for Session 1

**Use when:**
- Starting the workshop fresh
- Want to follow along from the beginning

**Switch to:**
```bash
git checkout main
```

---

### `session-1-complete`
**What's included:**
- Everything from main branch
- ✅ PLAN.md (comprehensive project plan)
- ✅ CLAUDE.md (project rules)
- ✅ Task entity with JPA annotations
- ✅ TaskStatus and Priority enums
- ✅ TaskRepository with query methods
- ✅ Repository tests (8 tests passing)

**Features:**
- Complete data model
- Foundation for REST API
- Query methods for filtering/search (used in Session 3)

**Use when:**
- Session 1 took longer than expected
- Want to start from Session 2
- Need reference implementation for planning

**Switch to:**
```bash
git checkout session-1-complete
```

**Test it:**
```bash
mvn clean test
# Should pass: BasicSetupTest + TaskRepositoryTest (9 tests total)
```

---

### `session-2-complete`
**What's included:**
- Everything from session-1-complete
- ✅ TaskService (business logic layer)
- ✅ TaskController (5 REST endpoints)
- ✅ Custom exceptions and error handling
- ✅ GlobalExceptionHandler
- ✅ Service unit tests (10 tests)
- ✅ Controller integration tests (11 tests)
- ✅ Custom `/api-endpoint` skill

**Features:**
- Full REST API operational
- CRUD operations working
- Proper HTTP status codes
- Validation and error responses
- 30+ tests passing

**Use when:**
- Session 2 took longer than expected
- Want to start from Session 3
- Need reference for REST API patterns

**Switch to:**
```bash
git checkout session-2-complete
```

**Test it:**
```bash
mvn clean test
# Should pass: 30+ tests

# Start the API
mvn spring-boot:run

# Test endpoints (in another terminal)
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","description":"Testing API","status":"PENDING"}'

curl http://localhost:8080/api/tasks
```

---

### `session-3-complete`
**What's included:**
- Everything from session-2-complete
- ✅ Filtering by status working
- ✅ Search by keyword operational
- ✅ Combined filters
- ✅ Session 3 notes for agent usage

**Features:**
- Filter tasks: `GET /api/tasks?status=PENDING`
- Search tasks: `GET /api/tasks?search=keyword`
- Combined: `GET /api/tasks?status=PENDING&search=urgent`
- Case-insensitive search
- Null-safe query handling

**Session 3 Focus:**
- Use agents to review code (not add new code)
- Plan agent: Design improvements
- code-review agent: Analyze quality
- Explore agent: Find patterns
- Apply refactorings based on feedback

**Use when:**
- Session 3 took longer than expected
- Want to start from Session 4
- Need reference for filtering/search patterns

**Switch to:**
```bash
git checkout session-3-complete
```

**Test it:**
```bash
mvn spring-boot:run

# Test filtering
curl "http://localhost:8080/api/tasks?status=PENDING"

# Test search
curl "http://localhost:8080/api/tasks?search=important"

# Test combined
curl "http://localhost:8080/api/tasks?status=COMPLETED&search=bug"
```

---

### `session-4-complete` (Final Solution)
**What's included:**
- Everything from session-3-complete
- ✅ Task assignments (assignee field)
- ✅ Task tags (Set<String>)
- ✅ NotionIntegrationService (MCP stub)
- ✅ Session 4 documentation
- ✅ Complete workflow demonstrated

**Features:**
- Full Task Management API
- All CRUD operations
- Filtering and search
- Assignments and tags
- MCP integration pattern (requires configuration)

**Use when:**
- Want to see the final solution
- Need reference for MCP integration
- Comparing your implementation

**Switch to:**
```bash
git checkout session-4-complete
```

**Test it:**
```bash
mvn clean test
# All tests pass

mvn spring-boot:run

# Create task with assignment
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Review PR",
    "description": "Urgent review needed",
    "assignee": "dev@example.com",
    "tags": ["code-review", "urgent"],
    "priority": "HIGH",
    "status": "PENDING"
  }'
```

---

## How to Use Checkpoints

### If You Fall Behind
```bash
# Check which branch you're on
git branch

# Switch to the checkpoint for the next session
git checkout session-X-complete

# Verify it works
mvn clean test
```

### If You Want to Compare
```bash
# See differences between your work and checkpoint
git diff session-2-complete

# See what changed in a specific commit
git show ef640ab
```

### If You Want to Start Over
```bash
# Discard all changes and reset to checkpoint
git checkout session-2-complete
git reset --hard

# Or go back to the starter code
git checkout main
```

## Branch Commit History

```
main (latest)
├── session-4-complete: MCP integration + assignments
├── session-3-complete: Filtering/search + agent guidance
├── session-2-complete: REST API + service layer
├── session-1-complete: Planning + foundation
└── Initial workshop starter structure
```

## Testing Each Checkpoint

### Quick Validation
```bash
# Switch to a checkpoint
git checkout session-X-complete

# Compile
mvn clean compile

# Run tests
mvn test

# Start app (Sessions 2+)
mvn spring-boot:run
```

### Expected Test Counts

| Branch | Test Files | Test Methods | Status |
|--------|------------|--------------|--------|
| main | 1 | 1 | ✅ Pass |
| session-1-complete | 2 | 9 | ✅ Pass |
| session-2-complete | 4 | 30+ | ✅ Pass |
| session-3-complete | 4 | 30+ | ✅ Pass |
| session-4-complete | 4 | 30+ | ✅ Pass |

## Tips for Workshop Facilitators

1. **Pre-Workshop**: Test all checkpoints work on your machine
2. **During Workshop**: Be ready to help participants switch branches
3. **Time Management**: Use checkpoints if sessions run over time
4. **Debugging**: If a participant's code has issues, compare with checkpoint
5. **Learning**: Encourage participants to explore checkpoint code after workshop

## Getting Help

If a checkpoint doesn't work:

1. **Check Java/Maven versions**: `java -version` and `mvn -version`
2. **Clean build**: `mvn clean install`
3. **Check Git status**: `git status` (should be clean)
4. **Read logs**: Look at test output and error messages
5. **Compare code**: Use `git diff` to see what's different

---

## Workshop Structure Summary

| Session | Duration | Checkpoint | Key Learning |
|---------|----------|------------|--------------|
| 1 | 25 min | session-1-complete | Planning & Foundation |
| 2 | 25 min | session-2-complete | REST API & Skills |
| 3 | 25 min | session-3-complete | Agents & Advanced Features |
| 4 | 30 min | session-4-complete | MCP Integration |

**Total**: ~105 minutes + buffer

---

All checkpoints are tested and working. Happy learning! 🚀
