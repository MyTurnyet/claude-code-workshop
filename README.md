# Claude Code Workshop: Task-Based Development for Java Developers

A hands-on workshop teaching mid-level Java developers how to use Claude CLI for software development through building a Task Management REST API.

## Workshop Overview

In this 2-hour workshop, you'll learn Claude Code's unique approach to software development by building a complete Spring Boot REST API incrementally across 4 sessions:

- **Session 1 (25 min)**: Planning-Driven Development - Create planning documents and implement basic entities
- **Session 2 (25 min)**: Rules & Skills - Build REST API and create custom automation
- **Session 3 (25 min)**: Agents - Use specialized agents for complex tasks
- **Session 4 (30 min)**: MCP Integration - Connect to external services

## What You'll Build

A fully functional Task Management REST API with:
- ✅ Task CRUD operations (Create, Read, Update, Delete)
- ✅ RESTful API endpoints with proper HTTP methods
- ✅ Task filtering and search capabilities
- ✅ Task assignments and tags
- ✅ External service integration via MCP (Notion or Google Calendar)
- ✅ Comprehensive test coverage

## Prerequisites

Before starting the workshop, ensure you have:

- **Java 17+** installed and configured
- **Maven 3.6+** for building the project
- **Claude CLI** installed and authenticated (see [Claude CLI setup](https://docs.anthropic.com/claude/docs/claude-cli))
- **IDE** (VSCode or IntelliJ IDEA) with Claude plugin
- **Git** for version control
- **Notion account** (optional, for Session 4 MCP integration)

### Verify Your Setup

Run these commands to verify your environment:

```bash
# Check Java version (should be 17+)
java -version

# Check Maven version
mvn -version

# Check Claude CLI
claude --version

# Clone this repository
git clone <your-repo-url>
cd claude-code-workshop

# Verify project compiles
mvn clean compile

# Run tests (should pass with 1 test)
mvn test
```

If the test passes, you're ready to start!

## Project Structure

```
claude-code-workshop/
├── README.md                          # This file
├── WORKSHOP-GUIDE.md                  # Detailed step-by-step instructions
├── pom.xml                            # Maven configuration
├── docs/
│   ├── PLAN-template.md               # Template for planning documents
│   ├── PLAN-complete.md               # Complete example plan
│   ├── CLAUDE-template.md             # Template for project rules
│   └── session-notes/                 # Detailed notes for each session
├── src/
│   ├── main/java/com/workshop/taskapi/
│   │   ├── TaskApiApplication.java    # Spring Boot main class
│   │   ├── model/                     # Empty - you'll create Task entity
│   │   ├── repository/                # Empty - you'll create repositories
│   │   ├── controller/                # Empty - you'll create REST controllers
│   │   ├── service/                   # Empty - you'll create services
│   │   └── integration/               # Empty - you'll add MCP integration
│   └── test/                          # Test classes
└── .claude/
    └── skills/                        # Custom skills created in Session 2
```

## Getting Started

### Option 1: Start Fresh (Recommended for Learning)

Start from scratch and build everything with Claude's help:

1. Open the project in your IDE
2. Read `WORKSHOP-GUIDE.md` for detailed instructions
3. Begin Session 1!

### Option 2: Use Checkpoint Branches

If you fall behind or want to start from a specific session, use checkpoint branches:

```bash
# See available checkpoints
git branch -a

# Jump to Session 2 starting point
git checkout session-1-complete

# Jump to Session 3 starting point
git checkout session-2-complete

# Jump to Session 4 starting point
git checkout session-3-complete

# See final completed solution
git checkout session-4-complete
```

## Workshop Sessions

### Session 1: Planning-Driven Development (25 minutes)
**Learn**: How to create effective planning documents and use them to guide development

**Build**:
- `PLAN.md` - Comprehensive project plan
- `CLAUDE.md` - Project rules and conventions
- `Task.java` - Basic entity with JPA annotations
- `TaskRepository.java` - Data access layer
- Unit tests

### Session 2: Rules & Skills (25 minutes)
**Learn**: How rules enforce consistency and custom skills automate workflows

**Build**:
- `TaskController.java` - REST API endpoints
- `TaskService.java` - Business logic
- Custom `/api-endpoint` skill
- Integration tests

### Session 3: Agents (25 minutes)
**Learn**: When and how to use specialized agents for complex tasks

**Build**:
- Task filtering and search endpoints
- Agent-driven code reviews
- Refactored code based on feedback
- Plan for next feature (created by agent)

### Session 4: MCP Integration (30 minutes)
**Learn**: How to integrate external services using Model Context Protocol

**Build**:
- Task assignments feature
- MCP integration (Notion or Google Calendar)
- Complete end-to-end workflow
- Final tests and validation

## Tips for Success

1. **Follow the Plan**: Create `PLAN.md` in Session 1 and reference it throughout
2. **Use Rules**: Let `CLAUDE.md` enforce consistency automatically
3. **Ask Questions**: Claude Code works best with clear, specific prompts
4. **Iterate**: Build incrementally, test frequently
5. **Use Checkpoints**: Don't hesitate to use checkpoint branches if needed
6. **Experiment**: Try different approaches, learn from mistakes

## Common Issues

### Maven build fails
```bash
# Clean and rebuild
mvn clean install

# Skip tests if needed
mvn clean install -DskipTests
```

### Tests fail
```bash
# Run tests with verbose output
mvn test -X

# Run specific test
mvn test -Dtest=BasicSetupTest
```

### H2 Database issues
- Check `application.properties` configuration
- Access H2 console at http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:taskdb`

### Claude CLI issues
```bash
# Re-authenticate
claude auth login

# Check configuration
claude config list
```

## Getting Help

- **During Workshop**: Ask the instructor or teaching assistant
- **Documentation**: See `docs/` folder for detailed guides
- **Troubleshooting**: Check `docs/TROUBLESHOOTING.md` (if available)
- **Claude CLI Help**: Run `claude --help` or visit [documentation](https://docs.anthropic.com/claude/docs)

## Next Steps

After completing the workshop:

1. Review `docs/BONUS-CHALLENGES.md` for additional exercises
2. Apply Claude Code to your own projects
3. Create custom skills for your team's workflows
4. Explore more MCP servers for different integrations
5. Share your experience and learnings with others

## Resources

- [Claude Code Documentation](https://docs.anthropic.com/claude/docs/claude-cli)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Model Context Protocol](https://modelcontextprotocol.io/)
- Workshop Plan: `/Users/paige.watson/IdeaProjects/documents/claude-code-workshop-plan.md`

## License

This workshop is provided for educational purposes. See LICENSE file for details.

---

**Ready to start?** Open `WORKSHOP-GUIDE.md` and begin Session 1!
