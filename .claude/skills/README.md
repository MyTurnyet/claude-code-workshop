# Custom Claude Code Skills

This directory contains custom skills created during the workshop.

## What Are Skills?

Skills are reusable automation scripts that Claude Code can execute. They help you:
- Automate repetitive tasks
- Standardize patterns across your team
- Speed up development workflows
- Reduce cognitive load

## Workshop Skills

### Session 2: API Endpoint Skill

In Session 2, you'll create your first skill: `/api-endpoint`

**Purpose**: Scaffold new REST API endpoints automatically

**What it does**:
- Creates controller method with proper annotations
- Creates corresponding service method
- Generates basic integration tests
- Follows CLAUDE.md conventions automatically

### Creating Your First Skill

Skills are stored in subdirectories under `.claude/skills/`:

```
.claude/skills/
└── api-endpoint/
    ├── skill.md          # Instructions for Claude
    └── README.md         # Documentation for developers
```

**skill.md** contains instructions that Claude follows when the skill is invoked.

**README.md** explains what the skill does for human developers.

## Invoking Skills

Use skills by typing a slash command:

```
/api-endpoint
```

Claude will then guide you through the skill's workflow.

## Skill Best Practices

1. **Keep Skills Focused**: One skill = one task
2. **Clear Instructions**: Be specific in skill.md
3. **Follow Project Rules**: Reference CLAUDE.md in skills
4. **Document Usage**: Include examples in README
5. **Share with Team**: Skills work for everyone on the project

## Example Skills You Might Create

After the workshop, consider creating skills for:

- `/test-endpoint` - Generate integration tests for an endpoint
- `/crud-entity` - Scaffold a complete CRUD entity with repository
- `/api-docs` - Generate OpenAPI documentation for endpoints
- `/refactor-check` - Run code review agent on a file
- `/deploy-prep` - Checklist for deployment preparation

## Learning More

- [Claude Code Skills Documentation](https://docs.anthropic.com/claude/docs/skills)
- Workshop Guide: `WORKSHOP-GUIDE.md` Session 2
- Session Notes: `docs/session-notes/session-2-skills.md`

---

**Ready to create your first skill?** Continue with Session 2 of the workshop!
