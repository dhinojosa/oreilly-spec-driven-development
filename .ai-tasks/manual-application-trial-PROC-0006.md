# PROC-0006 Manual Application Trial Instructions

Governed by: `PROC-0006`

Cucumber specification: Not applicable. This task adds instructions for running
and trying the existing application. It does not introduce or change
application behavior.

Document covered by this procedure:

```text
README.md
```

## Prerequisites

- [x] Document the required Java and Maven versions.
- [x] Document that Docker must be running.
- [x] Document that the application image is built locally with Jib before
  Docker Compose is started.

## Run The Application

- [x] Add the command that builds the `full-application` image in the local
  Docker engine.
- [x] Add the command that starts the existing application and PostgreSQL
  services from `full-application-e2e/docker-compose.yml`.
- [x] Tell the reader to open `http://localhost:8080`.

## Try The Application

- [x] Explain that the database does not contain a ready-made application
  account.
- [x] Tell the reader to register this example account:

```text
User name: casey
Password: correct-horse-battery-staple
```

- [x] Tell the reader to log out and log back in with the same user name and
  password.
- [x] Point out the available account and Todo Today behavior that can be tried
  manually.

## Stop The Application And Run Tests

- [x] Add the Docker Compose command that stops the manually running
  application and database.
- [x] Clearly state that the manually running Compose services must be stopped
  before the end-to-end tests are run.
- [x] Add the command that runs the `full-application-e2e` tests.
- [x] State that Testcontainers starts and stops the Compose services for the
  end-to-end test run.

## Scope

- [x] Keep these instructions in plain English and present the commands in the
  order they are used.
- [x] Do not change Jib, Docker Compose, Maven configuration, application code,
  or tests while executing this procedure.
- [x] If the documented commands do not match the existing configuration,
  report the mismatch instead of changing technical configuration under this
  PROC.

Executing this procedure changes only `README.md` and this task's checkboxes. It
does not run Maven or Docker and does not change application or build
configuration.
