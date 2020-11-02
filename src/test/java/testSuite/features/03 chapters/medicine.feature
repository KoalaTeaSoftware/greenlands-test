@wip
Feature: Chapter: Medicine

  Scenario: See that the page is complete
    Given I navigate to the page "medicine"
    Then the page title is "The Greenlands | medicine"
    And the secondary nav links are present

  Scenario: See that the images are correctly shown
  This is a minor risk as the image files are not even served up from a CDN provided through WordPress
    Given I navigate to the page "medicine"
    Then all images are well drawn

  Scenario: HTML Compliance with W3C standards
    Given the w3C HTML tester reviews the file "medicine"
    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
  There are one, or two links that are specific to the page
    Given the w3c link checker reviews the file "medicine"
    Then the w3c link checker reports compliance