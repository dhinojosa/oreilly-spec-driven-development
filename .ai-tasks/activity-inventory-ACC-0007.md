# ACC-0007 Activity Inventory

Governed by: `@ACC-0007`

Feature file:

```text
full-application-acceptance/src/test/resources/com/evolutionnext/features/activityinventory/activity-inventory.feature
```

Step definition path:

```text
full-application-acceptance/src/test/java/com/evolutionnext/features/activityinventory
```

Business behavior summary:

A logged-in user can add a named, high-priority activity to the activity
inventory. The activity remains available when the user leaves and returns to
the activity inventory page.

This slice does not move an activity into Todo Today or complete an activity.
Those behaviors require later acceptance specifications that define the link
between an activity and its Todo Today item.

## Business Language And Specification

- [x] Add `activity`, `activity inventory`, `priority`, and `high priority` to
  `.memory-bank/ubiquitous-language.md`.
- [x] Define an activity as work recorded for possible future selection, not as
  a Todo Today item.
- [x] Move the feature file to the project package path
  `com/evolutionnext/features/activityinventory/activity-inventory.feature`
  without changing its approved behavior or `@ACC-0007` tag.

## Domain Aggregate

- [x] Create an `ActivityInventory` aggregate for one logged-in user.
- [x] Create stable activity identity, activity name, and priority domain
  values.
- [x] Support adding a high-priority activity to the inventory.
- [x] Keep Todo Today selection and activity completion out of this aggregate
  for ACC-0007.
- [x] Add example-based aggregate tests for adding and reading a high-priority
  activity.
- [x] Add a jqwik property test proving that every generated valid activity
  added to an inventory remains present with its original priority.

## Domain Service

- [x] Record that a domain service is unnecessary because adding an activity
  changes only one activity inventory aggregate.
- [x] Keep the add-activity rule inside the aggregate.

## Application Service

- [x] Create a logged-in activity inventory command port with an add-activity
  command.
- [x] Create a logged-in activity inventory query port that returns the
  current user's inventory.
- [x] Implement separate command and query application services.
- [x] Add example-based service tests for adding and reading an activity.
- [x] Add a jqwik property test proving that every accepted add-activity
  command is visible through the query port with the same priority.

## Repository

- [x] Create an activity inventory repository output port.
- [x] Create an in-memory repository adapter for acceptance, controller, and UI
  tests.
- [x] Create a JDBC repository adapter that stores activities by stable
  identity and logged-in user.
- [x] Add the activity inventory table to
  `full-application-e2e/init.sql`.
- [x] Add an in-memory repository property test proving that every generated
  saved activity can be read for its user.
- [x] Add a PostgreSQL repository property test using jqwik,
  jqwik-testcontainers, and Testcontainers proving that every generated saved
  activity can be read for its user after a new query.

## Domain Event

- [x] Record that no domain event is needed because ACC-0007 has no behavior
  outside the activity inventory feature.
- [x] Do not introduce a completion event until a later acceptance
  specification defines Todo Today linking and activity completion.

## Controller

- [x] Create a dedicated `ActivityInventoryHttpHandler` under the activity
  inventory feature.
- [x] Replace the account handler currently serving `/activity-inventory` with
  the dedicated activity inventory handler.
- [x] Require a logged-in user for activity inventory requests.
- [x] Serve the activity inventory page and accept an add-activity form
  submission.
- [x] Show each saved activity name and its high priority on the page.
- [x] Wire the in-memory repository when no database is configured and the JDBC
  repository when database settings are present.
- [x] Add example-based controller tests for authentication, adding an
  activity, and seeing it after leaving and returning.
- [x] Add a jqwik controller property test proving that every successful
  add-activity POST is visible through a later GET for the same user.
- [x] Confirm the existing Hexagonal ArchUnit rules cover the new feature
  packages; extend the rules only if a required boundary is not currently
  enforced.

## E2E Testing

- [x] Create the ACC-0007 Cucumber runner, scenario state, and step definitions
  under the activity inventory acceptance package.
- [x] Implement both approved scenarios without changing their business
  wording.
- [x] Add a REST Assured E2E test that registers and logs in a user, adds a
  high-priority activity through the packaged application, and reads it back
  after another page request.
- [x] Reuse the existing Jib application image, Docker Compose, PostgreSQL, and
  Testcontainers setup; do not redesign that setup for this slice.
- [x] Create an `ActivityInventoryPage` Page Object for navigation, form entry,
  submission, page-specific waiting, and reading displayed activities.
- [x] Create an activity Page Component if the displayed activity has multiple
  fields or behavior that should be read together.
- [x] Add a Selenium E2E test whose user flow and expected results remain in
  the test while all selectors, interactions, and waits remain in Page Objects
  or Page Components.
- [x] Verify through the browser that the user adds "Call Mother" with high
  priority, leaves the page, returns, and still sees the activity with high
  priority.
- [x] Confirm the Page Object ArchUnit test passes for the new UI test.

## Validation

- [x] Run the focused ACC-0007 Cucumber tests.
- [x] Run the activity inventory domain, application service, repository, and
  controller tests through Maven.
- [x] Run the focused Hexagonal and Page Object ArchUnit tests.
- [x] Build the full application image with the existing Jib configuration.
- [x] Run the packaged REST Assured and Selenium E2E tests through the existing
  Testcontainers-managed Docker Compose environment.
- [x] Run `mvn verify`.
- [x] Confirm ACC-0007 has passing example-based, property-based, architecture,
  API, and Page Object UI verification with no accepted test gap.

## Validation Notes

- Focused domain, application service, repository, controller, Cucumber,
  Hexagonal architecture, REST Assured, Selenium Page Object, and Page Object
  architecture tests passed.
- The full `mvn verify` run passed the application and acceptance modules but
  failed in `full-application-e2e` when all three Safari tests timed out during
  navigation after registration.
- A clean `mvn -pl full-application-e2e test` rerun reproduced the same Safari
  timeouts. The packaged REST Assured tests and Page Object architecture test
  continued to pass.
- Full verification was initially left open pending direction on the Safari
  test failure.
- Added shared Page Object wait diagnostics that report the current URL,
  visible body text, and a bounded page-source snapshot if Safari times out.
- A complete `mvn -pl full-application-e2e test` rerun passed 6 tests with no
  failures, errors, or skips.
- The final `mvn verify` rerun completed successfully with no validation gap.
