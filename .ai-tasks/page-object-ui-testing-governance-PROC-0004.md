# PROC-0004 Page Object UI Testing Procedure

Governed by: `PROC-0004`

Cucumber specification: Not applicable. This task updates the documented UI
testing procedure and does not introduce or change application behavior.

Documents covered by this procedure:

```text
AGENTS.md
.memory-bank/project.md
.memory-bank/architecture.md
.memory-bank/spec-driven-development.md
.memory-bank/task-workflow.md
```

## Existing E2E Process

Keep the existing end-to-end process unchanged:

- Jib creates the `full-application` image.
- `full-application-e2e` runs Docker Compose through Testcontainers.
- Docker Compose runs the application with its database.
- REST Assured tests applicable API behavior.
- Selenium tests applicable browser behavior.

This procedure does not redesign or replace that process.

## Procedure Changes

- [x] Document that Selenium UI tests use Page Objects.
- [x] Document that selectors, form entry, button clicks, navigation, and
  browser waiting belong in Page Objects, Page Components, or shared browser
  support.
- [x] Document that UI tests describe the user flow and check the expected
  results.
- [x] Document that Page Object methods are named after what a user does or
  sees, such as `register`, `logIn`, `openTodoToday`, or `greetingText`.
- [x] Document that browser waiting looks for a specific page, element, text,
  or result instead of pausing for an arbitrary amount of time.
- [x] Document that plans for browser-visible behavior identify the Page
  Objects or Page Components that will be created or updated.
- [x] Document that browser-visible behavior is incomplete until its Page
  Object-based UI test runs successfully.
- [x] Document that a governed task explains why UI testing does not apply when
  it is omitted.

## Documents To Update

- [x] Update `AGENTS.md` with the Page Object rule.
- [x] Update `.memory-bank/project.md` with the high-level UI completion rule.
- [x] Update `.memory-bank/architecture.md` with Page Object responsibilities.
- [x] Update `.memory-bank/spec-driven-development.md` with the Page
  Object-based UI completion rule.
- [x] Update `.memory-bank/task-workflow.md` with Page Object planning and
  validation requirements.

## Work Kept In TECH-0006

The following implementation work is not part of this procedure:

- Creating or changing Java Page Objects or Page Components.
- Refactoring Selenium UI tests.
- Adding or changing ArchUnit dependencies or architecture tests.
- Changing application code, Cucumber specifications, build configuration,
  Jib, Testcontainers, Docker Compose, database setup, or REST Assured tests.
- Running the Page Object-based browser tests.

Executing this procedure changes only Markdown documentation and its task
checkboxes. It does not run Maven, build an image, run tests, or change
application code.
