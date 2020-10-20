@smoke
Feature: Automatic screen grab when a scenario fails
  Whenever a step fails (causing a scenario to fail) a screen grab will be captured.
  If you see the screen grab in the report, then this smoke test passes.

  Scenario: Fail a test and get a screen grab
    When I navigate to the page "http://koalateasoftware.com"
    And I write to the html report "The following step will cause the scenario to fail. "
    Then the page title is "qwertyuiop"
    And I write to the html report "This step should be skipped"