# Agent Instructions

This repository is for spec-driven-development.

Before making feature changes, read:

- `.memory-bank/project.md`
- `.memory-bank/architecture.md`
- `.memory-bank/spec-driven-development.md`
- `.memory-bank/task-workflow.md`
- `.memory-bank/ubiquitous-language.md`

## Required Workflow

- Business behavior starts from Cucumber specs in `full-application-acceptance`.
- Governed specs use identifier tags such as `@ACC-0001`.
- Behavioral revisions to an existing governed feature use suffixed tags such as `@ACC-0001-02`.
- Each governed spec must have a matching task file in `.ai-tasks/`.
- Create a new suffixed task file when a spec change alters behavior, flow, validation, output, or acceptance intent.
- Update the existing task only when the spec edit is non-behavioral wording or clarification.
- Prefer scenario-level revision tags when only one scenario changed.
- Keep specs, tasks, tests, classes, handlers, routes, and user-facing copy aligned to `.memory-bank/ubiquitous-language.md`.
- Show the task plan before implementing feature behavior.
- Implement features as vertical slices through the relevant modules.
- Mark completed task checkboxes when work is done.
- Do not treat a feature as complete unless the slice is test-complete.
- When behavior is visible in the browser, test it through Page Objects before
  calling the slice complete unless the user explicitly accepts the gap.
- Keep Selenium selectors, form entry, button clicks, navigation, and browser
  waiting in Page Objects, Page Components, or shared browser support.
- Keep the user flow and checks of expected results in the UI test.

## Stuck Work And Verification

- The five-minute limit begins when work becomes stuck, not when the whole task
  begins.
- Repeated failures, unclear requirements, unavailable tools, or uncertain
  workarounds mean the work may be stuck.
- If clear progress is not made within five minutes, stop, report what was
  attempted and what failed, and ask the user for direction before trying
  another approach.
- Do not hide a difficulty with an unrelated workaround.
- A command running normally for more than five minutes is not automatically
  stuck; continue to report its progress.
- Prefer Java testing tools for verifying Java application behavior, and prefer
  Maven for compiling and running verification.
- Use the existing test suite before creating an extra verification test.
- Do not create Python scripts, replacement modules, or other shims in `/tmp`,
  `/private/tmp`, or another temporary directory to imitate missing project
  tools.
- Put tests created only to verify the agent's work under
  `com.evolutionnext.aiverify.<feature>`.
- Keep tests required by ACC and TECH tasks in their normal feature packages;
  `aiverify` tests do not replace them.

## Build

Use SDKMAN and prefer the repository's `.sdkmanrc` through `sdk env` so agents
and students use the same configured toolchain:

```bash
sdk env
mvn validate
```

For implementation changes, run the full available Maven verification suite:

```bash
mvn verify
```

If full verification is blocked by environment constraints, report the blocker,
run the strongest narrower validation available, and document the validation gap
in the relevant task file.

## Repository Shape

- `full-application`: application implementation
- `full-application-acceptance`: Cucumber acceptance specifications
- `full-application-e2e`: end-to-end verification
- `.ai-tasks`: task plans governed by spec identifiers
- `.memory-bank`: durable project, architecture, and workflow context
