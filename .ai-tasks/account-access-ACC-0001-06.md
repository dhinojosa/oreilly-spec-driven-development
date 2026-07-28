# ACC-0001-06 Account Access Personalized Dashboard Greeting

Governed by: `@ACC-0001-06`

Feature file:

```text
full-application-acceptance/src/test/resources/com/evolutionnext/features/account/account-access.feature
```

Step definition path:

```text
full-application-acceptance/src/test/java/com/evolutionnext/features/account
```

Business behavior summary:

After a registered user logs in, the dashboard page shows a personalized
greeting using the logged-in user's display name, such as `Hello, Casey`.

## Domain Aggregate

- [x] Confirm whether account user name capitalization for the greeting is a presentation concern or an account domain rule.
- [x] Keep the account aggregate unchanged if the greeting can be derived from existing account user name state.
- [x] Add or update account aggregate tests only if a new display-name rule is introduced.

## Domain Service

- [x] Confirm no domain service is required if personalized greeting formatting is simple presentation behavior.
- [x] Document that no domain service is introduced unless greeting rules grow beyond direct user name formatting.
- [x] Skip domain service tests if no domain service behavior changes.

## Application Service

- [x] Expose the authenticated user's account information to the dashboard rendering path using the existing account query/application-service boundary where practical.
- [x] Test at the application-service level only if a new query result or authenticated account lookup behavior is added.
- [x] Include jqwik coverage only if a new formatting or lookup rule is introduced at the application-service boundary.

## Repository

- [x] Keep repository contracts unchanged if the existing account lookup supports resolving the logged-in user's user name.
- [x] Add repository tests only if a new lookup contract is required for dashboard personalization.
- [x] Include Testcontainers coverage if a new JDBC repository query is added.

## Domain Event

- [x] Keep domain events unchanged because showing a personalized dashboard greeting does not represent a new account state transition.
- [x] Document that no new account event is emitted for greeting rendering.
- [x] Skip domain event tests because no domain event behavior changes.

## Controller

- [x] Add the Cucumber step definition for `the dashboard page shows the personalized greeting {string}`.
- [x] Update dashboard rendering so successful login includes a personalized greeting for the authenticated user.
- [x] Ensure successful registration still reaches the dashboard page and either shows the same personalized greeting or records why this revision only covers login.
- [x] Add controller tests for successful login rendering `Hello, Casey` on the dashboard page.
- [x] Add controller tests for the greeting using the authenticated account rather than a hard-coded value.
- [x] Preserve existing dashboard navigation to the todo today page, activity inventory page, record sheet page, and log out.

## E2E Testing

- [x] Verify the `@ACC-0001-06` Cucumber scenario passes.
- [x] Add or update Selenium coverage in `full-application-e2e` because the personalized greeting is browser-visible dashboard behavior.
- [x] Verify browser-level login reaches the dashboard page and shows `Hello, Casey`.
- [x] Keep existing account-access and todo-today E2E behavior passing.

## Validation

- [x] Run `mvn verify`.
- [x] Confirm the `@ACC-0001-06` scenario passes.
- [x] Confirm existing `@ACC-0001-02`, `@ACC-0001-03`, `@ACC-0001-04`, and `@ACC-0001-05` account access scenarios still pass.
- [x] Confirm Selenium browser-level verification runs and passes for the personalized dashboard greeting.
- [x] Record any browser-driver or local UI automation blocker in this task file if browser-level verification cannot run.

Validation notes:

- User name capitalization is presentation behavior in `AccountHttpHandler`; the account aggregate, domain services, application services, repository contracts, and domain events were unchanged.
- `mvn -pl full-application -Dtest=AccountHttpHandlerTest test` passed.
- `mvn -pl full-application-acceptance -am -Dtest=RunAccountAccessCucumberTest -Dsurefire.failIfNoSpecifiedTests=false test` passed with 9 account scenarios, including `@ACC-0001-06`.
- `mvn -pl full-application-e2e -am test` passed; `AccountAccessSeleniumE2ETest` verified browser login shows `Hello, Casey`.
- `mvn verify` passed.
- `AccountAccessE2ETest` still reports one skipped Docker/REST Assured health test behind its pre-existing `RUN_E2E` gate; Selenium browser verification for this slice ran and passed.
