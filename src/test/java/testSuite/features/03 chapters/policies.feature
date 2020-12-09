@feature
Feature: Chapter: Policies
  This is a thoroughly static page, so has very little risk of regression.

  Scenario: See that the page is complete
    Given I navigate to the page "policies"
    Then the page title is "Policies"

  Scenario: HTML Compliance with W3C standards
    Given the w3C HTML tester reviews the file "policies"
    Then the w3c HTML tester reports compliance

  Scenario: See that the images are correctly shown
  This is a minor risk as the image files are not even served up from a CDN provided through WordPress
    Given I navigate to the page "policies"
    Then all images are well formed


  Scenario: Check links on a page
  There are one, or two links that are specific to the page
    Given the w3c link checker reviews the file "policies"
    Then the w3c link checker reports compliance
      | exceptions |
