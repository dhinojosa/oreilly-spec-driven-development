# TECH-0007 Remove The RUN_E2E Gate

Governed by: Technical task `TECH-0007`

Cucumber specification: Not applicable. This task changes how an existing
end-to-end test runs and does not change application behavior.

## Technical Change Summary

Running the `full-application-e2e` module already means that end-to-end tests
were selected. Remove the additional `RUN_E2E` environment-variable check so
the REST Assured test runs instead of quietly skipping itself.

Keep the existing end-to-end structure:

- Jib creates the `full-application` image.
- `full-application-e2e` runs Docker Compose through Testcontainers.
- Docker Compose runs the application with its database.
- REST Assured tests the running application.

## E2E Test Startup

- [ ] Remove the `RUN_E2E` environment-variable check from
  `AccountAccessE2ETest`.
- [ ] Remove instructions that require `RUN_E2E=true`.
- [ ] Use the existing Testcontainers support to start Docker Compose when the
  E2E test begins.
- [ ] Wait for the application and database to be ready before REST Assured
  sends a request.
- [ ] Use the application address and port provided by the Testcontainers
  environment.
- [ ] Stop the Testcontainers environment after the E2E test finishes.
- [ ] Do not require Docker Compose to be started manually.

## Maven Execution

- [ ] Treat selection of `full-application-e2e` as the instruction to run its
  E2E tests.
- [ ] Ensure the existing Jib-built application image is available before
  Docker Compose starts it.
- [ ] Fail with a clear error when required Docker infrastructure cannot start.
- [ ] Do not replace the removed gate with another environment-variable gate,
  Maven property gate, assumption, or silent skip.

## Unchanged Areas

- [ ] Keep application behavior unchanged.
- [ ] Keep Cucumber specifications and step definitions unchanged.
- [ ] Keep the existing Docker Compose services and database setup unchanged.
- [ ] Keep the REST Assured health-check behavior unchanged.
- [ ] Keep the Page Object-based Selenium tests unchanged.

## Validation

- [ ] Run `mvn -pl full-application-e2e -am test`.
- [ ] Confirm `AccountAccessE2ETest` executes with zero skipped tests.
- [ ] Confirm Testcontainers starts and stops Docker Compose.
- [ ] Confirm REST Assured receives `200 OK` from `/health`.
- [ ] Run `mvn verify`.
- [ ] Confirm no existing unit, integration, acceptance, API, architecture, or
  UI test regresses.
- [ ] Record any Docker or image-build failure instead of marking the affected
  work complete.
