# TECH-0005 E2E Module Selection

Governed by: Technical task

Business spec: Not applicable

Feature file path: Not applicable

Step definition path: Not applicable

Reason no Cucumber spec applies:

This task changes how browser-level E2E tests are selected and executed. It
does not add or revise business behavior; the existing E2E tests continue to
verify already-governed behavior.

Business behavior summary:

No business behavior changes. The task removes the hidden `RUN_UI_E2E` gate so
the `full-application-e2e` Maven module is the selection boundary for browser
verification.

## Domain Aggregate

- [x] Keep domain aggregates unchanged because E2E test selection has no domain model impact.

## Domain Service

- [x] Keep domain services unchanged because E2E test selection has no domain logic impact.

## Application Service

- [x] Keep application services unchanged because E2E test selection has no application flow impact.

## Repository

- [x] Keep repositories unchanged because E2E test selection has no persistence impact.

## Domain Event

- [x] Keep domain events unchanged because E2E test selection has no business event impact.

## Controller

- [x] Keep controllers unchanged because E2E test selection has no HTTP behavior impact.

## E2E Testing

- [x] Remove the `RUN_UI_E2E` assumption from `TodoTodaySeleniumE2ETest`.
- [x] Keep `TodoTodaySeleniumE2ETest` in `full-application-e2e` so Maven `-pl full-application-e2e` remains the explicit browser-test selection mechanism.
- [x] Keep the existing Selenium browser flow coverage for adding a task, setting an estimate, showing a large-task warning, setting completed pomodoros, and marking the task complete.
- [x] Stabilize the Selenium wait around completed-pomodoros input updates so the test does not fail with a stale element reference when the page re-renders.
- [x] Keep true external-prerequisite checks only if they detect unavailable browser infrastructure rather than silently skipping selected E2E behavior.

## Build / Tooling

- [x] Document that browser E2E tests run by selecting the `full-application-e2e` Maven module.
- [x] Avoid adding a replacement environment-variable gate for Selenium execution.
- [x] Verify root-level build behavior remains intentional after removing the test-level gate.

## Validation

- [x] Run `sdk env`.
- [x] Run `mvn -pl full-application-e2e -am test` and confirm `TodoTodaySeleniumE2ETest` executes instead of being skipped by `RUN_UI_E2E`.
- [x] Run `mvn test` and record whether root-level test execution intentionally includes the E2E module.
- [x] Run the strongest available Maven verification command for this tooling/test change.
- [x] Record any browser-driver or local UI automation blocker in this task file rather than marking Selenium verification complete.

Validation notes:

- `sdk env` selected Java `26-zulu` and Maven `4.0.0-rc-5`.
- `mvn -pl full-application-e2e -am test` passed; `TodoTodaySeleniumE2ETest` ran with Safari and was not skipped by `RUN_UI_E2E`.
- `mvn test` from the repository root passed; root-level test execution includes `full-application-e2e`, so Safari is launched by the Selenium test.
- `mvn verify` passed as the strongest available validation.
- `AccountAccessE2ETest` still reports one skipped test because of its pre-existing `RUN_E2E` Docker/environment gate; `TECH-0005` removed the Selenium `RUN_UI_E2E` gate only.
