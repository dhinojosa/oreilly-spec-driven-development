# TECH-0006 Enforce Page Object UI Testing

Governed by: Technical task `TECH-0006`

Related procedure:

```text
PROC-0004 Page Object UI Testing Governance
```

Cucumber specification: Not applicable. This task changes the structure and
enforcement of UI tests without changing application behavior.

## Technical Change Summary

Refactor Selenium UI tests to interact with the application through Page
Objects. Add an architecture test that prevents Selenium selectors, elements,
and waiting code from returning to UI test classes.

Keep the existing end-to-end process unchanged:

- Jib creates the `full-application` image.
- `full-application-e2e` runs Docker Compose through Testcontainers.
- Docker Compose runs the application with its database.
- REST Assured tests applicable API behavior.
- Selenium tests applicable browser behavior.

## Browser Support

- [x] Create shared browser support for navigation and waiting for expected
  pages, elements, text, and results.
- [x] Keep browser startup and shutdown in shared test support.
- [x] Do not use pauses for arbitrary amounts of time.
- [x] Keep the browser and application lifecycle used by the existing tests
  unchanged unless a change is required to support Page Objects.

## Page Objects

- [x] Create a Page Object for each page used by the existing account UI tests.
- [x] Create a Page Object for each page used by the existing todo-today UI
  tests.
- [x] Create Page Components for repeated page sections that have their own
  behavior, such as a todo item.
- [x] Keep Selenium selectors inside Page Objects or Page Components.
- [x] Keep form entry, button clicks, navigation, and waiting inside Page
  Objects or shared browser support.
- [x] Name methods after what a user does or sees, such as `register`, `logIn`,
  `openTodoToday`, or `greetingText`.
- [x] Return the resulting Page Object when an action navigates to another page.
- [x] Expose page content or state so the UI test can check the expected result.
- [x] Keep test assertions out of Page Objects and Page Components.

## Existing UI Tests

- [x] Refactor the account Selenium test to use Page Objects.
- [x] Refactor the todo-today Selenium test to use Page Objects and Page
  Components.
- [x] Keep the existing user flows and expected results unchanged.
- [x] Keep expected-result checks in the UI test classes.
- [x] Remove direct use of Selenium selectors, elements, and waiting APIs from
  UI test classes.

## Architecture Enforcement

- [x] Update ArchUnit to version `1.4.2`, which supports Java 26 class files.
- [x] Add the ArchUnit dependency to `full-application-e2e`.
- [x] Add an architecture test that prevents Selenium UI test classes from
  directly using `By`, `WebElement`, `WebDriverWait`, or
  `ExpectedConditions`.
- [x] Allow those Selenium APIs only in Page Objects, Page Components, and
  shared browser support.
- [x] Confirm that the architecture test fails if a Selenium selector or wait
  is placed directly in a UI test class.

## Unchanged Areas

- [x] Keep application behavior unchanged.
- [x] Keep Cucumber specifications and step definitions unchanged.
- [x] Keep the Jib image process unchanged.
- [x] Keep Testcontainers and Docker Compose setup unchanged.
- [x] Keep database setup unchanged.
- [x] Keep REST Assured API tests unchanged.

## Validation

- [x] Run `mvn verify`.
- [x] Confirm the account Page Object-based UI test executes and passes.
- [x] Confirm the todo-today Page Object-based UI test executes and passes.
- [x] Confirm the Page Object architecture test executes and passes.
- [x] Confirm no existing API, acceptance, integration, or unit test regresses.
- [x] Record any browser or Docker failure instead of marking the affected work
  complete.

Validation notes:

- `mvn verify` passed with Java `26-zulu` and Maven `4.0.0-rc-5`.
- `AccountAccessSeleniumE2ETest` and `TodoTodaySeleniumE2ETest` both executed
  through Page Objects and passed.
- `PageObjectArchitectureTest` executed and passed.
- A controlled direct `By` dependency in `AccountAccessSeleniumE2ETest` caused
  `PageObjectArchitectureTest` to fail with the expected architecture
  violation; the controlled dependency was then removed.
- The existing `AccountAccessE2ETest` health check remains skipped behind its
  pre-existing `RUN_E2E` gate. This task did not change REST Assured or its
  existing execution rules.
- No browser or Docker failure occurred.
