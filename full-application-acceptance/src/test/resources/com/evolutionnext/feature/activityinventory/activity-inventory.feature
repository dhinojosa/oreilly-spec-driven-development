@ACC-0007
Feature: Activity Inventory

    As a logged-in user
    I want to record activities I may do later
    So that I can choose work for the future

    Scenario: Add a high-priority activity
        Given a logged-in user is on the dashboard
        When the user opens the activity inventory page
        And the user adds an activity named "Call Mother" with high priority
        Then the activity inventory shows "Call Mother"
        And "Call Mother" is shown as high priority

    Scenario: An activity remains in the inventory
        Given a logged-in user has added "Call Mother" with high priority
        When the user leaves and returns to the activity inventory page
        Then the activity inventory shows "Call Mother"
        And "Call Mother" is shown as high priority
