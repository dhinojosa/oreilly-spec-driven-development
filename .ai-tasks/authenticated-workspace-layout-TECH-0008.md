# TECH-0008 Authenticated Workspace Layout

Governed by: Technical task `TECH-0008`

Cucumber specification: Not applicable. This task changes the visual layout of
the Todo Today and Activity Inventory pages without changing their business
behavior, routes, forms, or stored data.

## Layout

- [x] Use the full page width below the authenticated navigation.
- [x] Create a desktop workspace with an entry panel on the left and the page's
  main content on the right.
- [x] Give the entry panel roughly one quarter of the available width.
- [x] Give the main content roughly three quarters of the available width.
- [x] Keep the entry panel and main content visually distinct without adding
  unnecessary nested cards.

## Todo Today Page

- [x] Place the add-task form in the left entry panel.
- [x] Place the Todo Today item list in the right main panel.
- [x] Keep control of an existing Todo Today item with that item in the main
  panel.
- [x] Preserve all existing task entry, estimate, warning, pomodoro, and
  completion behavior.

## Activity Inventory Page

- [x] Place the add-activity form in the left entry panel.
- [x] Place the saved activity inventory in the right main panel.
- [x] Preserve the activity name and priority shown for each saved activity.
- [x] Preserve all existing activity entry and persistence behavior.

## Responsive Layout

- [x] At narrow screen widths, place the entry panel above the main content.
- [x] Keep form controls and item content usable without horizontal scrolling.
- [x] Keep the authenticated navigation usable at desktop and narrow widths.

## Page Object UI Testing

- [x] Update the Todo Today and Activity Inventory Page Objects with methods
  that expose the entry-panel and main-panel layout for verification.
- [x] Keep Selenium selectors and browser measurements inside Page Objects or
  shared browser support.
- [x] Add Page Object-based browser checks that the entry panel is left of the
  main panel at a desktop width.
- [x] Add Page Object-based browser checks that the desktop panels use an
  approximately 25/75-width split.
- [x] Add Page Object-based browser checks that the entry panel is above the
  main panel at a narrow width.
- [x] Keep layout expectations and assertions in the UI test classes.
- [x] Confirm the Page Object ArchUnit test still passes.

## Unchanged Areas

- [x] Keep Cucumber specifications and step definitions unchanged.
- [x] Keep domain models, application services, repositories, controllers,
  routes, and database schema unchanged.
- [x] Keep Jib, Docker Compose, Testcontainers, and REST Assured configuration
  unchanged.
- [x] Record that property-based and API tests are not applicable because this
  task changes layout only.

## Validation

- [x] Run the focused Todo Today and Activity Inventory Page Object UI tests at
  desktop and narrow widths.
- [x] Run the Page Object ArchUnit test.
- [x] Run `mvn verify`.
- [x] Confirm existing business, persistence, API, and UI behavior has not
  regressed.

## Validation Notes

- The focused packaged browser run passed the existing Todo Today and Activity
  Inventory flows, the new desktop and narrow layout checks, and the Page
  Object architecture test: 4 tests, 0 failures, and 0 skips.
- The desktop Page Object check confirmed that the entry panel is left of the
  main panel and occupies between 23% and 27% of their combined width.
- The narrow Page Object check confirmed that the entry panel is above the main
  panel and both panels have the same width.
- The final `mvn verify` completed successfully.
