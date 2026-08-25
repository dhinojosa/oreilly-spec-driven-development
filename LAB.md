# Lab

## Download the project

1. For anonymity, you can either clone direct from the repository or download the project as the zip file

## Using the project

1. If you have SDKMAN installed, run `sdk env`. The checked-in `.sdkmanrc`
   selects Java 26 and Maven 4.0.0-rc-5 for you.
2. If you do not use SDKMAN, install Java 26 and Maven 4.0.0-rc-5 by your
   preferred method and ensure `java` and `mvn` are available on your `PATH`.
3. Confirm the active toolchain with `java --version` and `mvn --version`, then
   run `mvn validate` from the project root.

## Introduction to your agent

1. Ask your agent if it understands the _AGENTS.md_ anchor and the _.memory-bank_
2. View the .memory-bank

## Choose a local application image name

Before building the application image, replace the instructor's image name with
a local name of your own. Use the same name in both places, so Docker Compose can
find the image that Jib builds.

1. In `full-application/pom.xml`, change the Jib target image to:

   ```xml
   <image>your-name/full-application:${project.version}</image>
   ```

2. In `full-application-e2e/docker-compose.yml`, change the application image
   to:

   ```yaml
   image: your-name/full-application:${FULL_APPLICATION_VERSION:-1.0-SNAPSHOT}
   ```

3. Use the architecture-appropriate `dockerBuild` command from `README.md`.
   `dockerBuild` loads the image into your local Docker daemon and does not push
   it to a registry. Do not replace the `dockerBuild` goal with `build`, because
   `build` pushes to the configured registry, unless that is what you want.

## Extend the activity inventory

Open
`full-application-acceptance/src/test/resources/com/evolutionnext/feature/activityinventory/activity-inventory.feature`
and add the following scenarios one at a time:

```gherkin
Scenario: Add a medium-priority activity
    Given a logged-in user is on the dashboard
    When the user opens the activity inventory page
    And the user adds an activity named "Prepare Workshop Slides" with medium priority
    Then the activity inventory shows "Prepare Workshop Slides"
    And "Prepare Workshop Slides" is shown as medium priority

Scenario: Add a low-priority activity
    Given a logged-in user is on the dashboard
    When the user opens the activity inventory page
    And the user adds an activity named "Organize Desk" with low priority
    Then the activity inventory shows "Organize Desk"
    And "Organize Desk" is shown as low priority

Scenario: Add an activity to Todo Today
    Given a logged-in user has added "Prepare Workshop Slides" with medium priority
    And the logged-in user has added "Organize Desk" with low priority
    When the user opens the activity inventory page
    Then "Prepare Workshop Slides" offers an "Add to Todo-Today" button
    And "Organize Desk" offers an "Add to Todo-Today" button
    When the user selects "Add to Todo-Today" for "Prepare Workshop Slides"
    And the user opens the todo today page
    Then the todo today page shows the task "Prepare Workshop Slides"
```

These scenarios change governed behavior. Assign the revision a new suffixed
identifier based on `ACC-0007` (for example, `@ACC-0007-02`) and add the tag to
the specification. Using the identifier you chose, without the `@`, tell your
agent, for example:

```text
PLAN ACC-0007-02
```

After reviewing the plan, tell your agent (using the same identifier):

```text
EXECUTE ACC-0007-02
```

### View the Application. 

In the _README.md_ file, under "Run And Try The Application", try to run the application and see what other features you can include. Note the features that you want. 

If it is not business-related, choose either:

* `TECH` for technical changes, for example, `TECH-0008 Add pit testing to the application`
* `PROC` for procedural changes, for example, `PROC-0006 Change a timeout for agents`
