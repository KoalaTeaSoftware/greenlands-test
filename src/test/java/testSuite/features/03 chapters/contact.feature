@feature
Feature: Contact form
  The form itself verifies the user's entries comply with various strictures and provides feedback when the form is submitted

  Scenario: See that the page is complete
    Given I navigate to the page "contact"
    Then the page title is "The Greenlands | contact"
    And the secondary nav links are present

  Scenario: HTML Compliance with W3C standards
    Given the w3C HTML tester reviews the file "contact"
    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
  There are one, or two links that are specific to the page
    Given the w3c link checker reviews the file "contact"
    Then the w3c link checker reports compliance

  @wip
  Scenario: all the various form entry stuff