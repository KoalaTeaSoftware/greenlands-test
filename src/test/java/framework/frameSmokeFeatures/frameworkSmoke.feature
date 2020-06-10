@framework
Feature: Basic Functionality
  As a test engineer
  So that I can tell if the test framework works
  I want to invoke the SUT and see some sort of result

  Background: See background is run at the correct moments
    Given I say on Stdout "Background steps are executed"
    And I say in the scenario report "Notes can be recorded in the pretty report"

  Scenario: Look at the default resource
    Given I say on Stdout "Steps for scenarios are also invoked"
    When I use browser "" to view the page at ""
    Then the browser page title contains ""

  Scenario: Fail a step
  Call up the default page, just to make sure there is stuff to be captured
  Otherwise you get a blank screenshot from this scenario
    Given I use browser "" to view the page at ""
    And the browser page title contains ""
    And I say in the scenario report "This scenario will fail, and put a screengrab in the report"
    And the step fails