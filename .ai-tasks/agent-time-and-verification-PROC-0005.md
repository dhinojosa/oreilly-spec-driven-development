# PROC-0005 Agent Time And Verification Procedure

Governed by: `PROC-0005`

Cucumber specification: Not applicable. This task updates the documented agent
procedure and does not introduce or change application behavior.

Documents covered by this procedure:

```text
AGENTS.md
.memory-bank/project.md
.memory-bank/task-workflow.md
```

## Five-Minute Rule

- [x] Document that the five-minute limit begins when work becomes stuck, not
  when the whole task begins.
- [x] Treat repeated failures, unclear requirements, unavailable tools, and
  uncertain workarounds as signs that work is stuck.
- [x] If clear progress is not made within five minutes, stop.
- [x] Report what was attempted, what failed, and what decision is needed.
- [x] Ask for direction before trying another approach.
- [x] Do not hide the difficulty with an unrelated workaround.
- [x] Document that a command running normally for more than five minutes is not
  automatically stuck and that its progress should continue to be reported.

## Verification Tools

- [x] Document that Java testing tools are preferred when verifying Java
  application behavior.
- [x] Document that Maven is preferred for compiling and running verification.
- [x] Document that the existing test suite is used before creating an extra
  verification test.
- [x] Prohibit Python scripts, replacement modules, and other shims in `/tmp`,
  `/private/tmp`, or another temporary directory from being used to imitate
  missing project tools.

## AI Verification Tests

- [x] Document that tests created only to verify the agent's work use the
  `com.evolutionnext.aiverify` package.
- [x] Group those tests by feature, for example:

```text
com.evolutionnext.aiverify.account
com.evolutionnext.aiverify.todotoday
```

- [x] Keep required domain, application, repository, controller, acceptance,
  API, and UI tests in their normal feature packages.
- [x] Do not use `aiverify` tests as replacements for tests required by an ACC
  or TECH task.
- [x] Run `aiverify` tests through Maven like other Java tests.

## Documents To Update

- [x] Update `AGENTS.md` with the five-minute stopping and reporting rule.
- [x] Update `AGENTS.md` with the Java, Maven, temporary-shim, and `aiverify`
  rules.
- [x] Update `.memory-bank/project.md` with the Java-and-Maven verification
  preference.
- [x] Update `.memory-bank/task-workflow.md` with the stopping, reporting,
  temporary-shim, and `aiverify` procedure.

Executing this procedure changes only Markdown documentation and its task
checkboxes. It does not run Maven, create tests, change POMs, or add Python
tooling.
