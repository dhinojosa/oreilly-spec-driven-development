# TECH-0004 SDKMAN Toolchain Configuration

Governed by: Technical task

Business spec: Not applicable

Feature file path: Not applicable

Step definition path: Not applicable

Reason no Cucumber spec applies:

This task configures the local development toolchain so Maven commands use the
project's expected Java and Maven versions. It does not introduce or alter
business behavior.

Business behavior summary:

No business behavior changes. The task only makes the repository toolchain
selection explicit for developers and automation that opt into SDKMAN
environment files.

## Domain Aggregate

- [x] Keep domain aggregates unchanged because SDKMAN configuration has no domain model impact.

## Domain Service

- [x] Keep domain services unchanged because SDKMAN configuration has no domain logic impact.

## Application Service

- [x] Keep application services unchanged because SDKMAN configuration has no application flow impact.

## Repository

- [x] Keep repositories unchanged because SDKMAN configuration has no persistence impact.

## Domain Event

- [x] Keep domain events unchanged because SDKMAN configuration has no business event impact.

## Controller

- [x] Keep controllers unchanged because SDKMAN configuration has no HTTP behavior impact.

## Build / Tooling

- [x] Create `.sdkmanrc` at the repository root.
- [x] Configure `.sdkmanrc` with `java=26-zulu`.
- [x] Configure `.sdkmanrc` with `maven=4.0.0-rc-5`.
- [x] Verify `.sdkmanrc` is not excluded by `.gitignore`.
- [x] Document the SDKMAN activation command if existing developer documentation does not already make it clear.

## E2E Testing

- [x] Keep E2E tests unchanged because SDKMAN configuration does not introduce browser-visible behavior or end-to-end application behavior.

## Validation

- [x] Run `sdk env` from the repository root and confirm SDKMAN selects Java `26-zulu` and Maven `4.0.0-rc-5`.
- [x] Run `mvn validate` after `sdk env` to confirm the configured Maven version satisfies the build prerequisites.
- [x] Run `mvn test` after `sdk env` to confirm the plain test command no longer fails because of Maven `3.9.x`.
- [x] Record any validation blocker in this task file if SDKMAN is unavailable or the configured candidates are not installed locally.

Validation notes:

- `sdk env` selected Java `26-zulu` and Maven `4.0.0-rc-5`.
- The sandboxed `mvn validate` attempt reached Maven `4.0.0-rc-5` but could not open `~/.m2/repository/.locks`; rerunning with normal local repository access passed.
- The first `mvn test` after `sdk env` no longer failed because of Maven `3.9.x`, but failed on stale compiled recordsheet test output in `target/test-classes`.
- `mvn clean test` removed the stale generated output and passed.
- A final plain `mvn test` after `sdk env` passed.
