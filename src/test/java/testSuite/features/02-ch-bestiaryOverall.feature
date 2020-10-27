@functional
Feature: Bestiary List - Overall
  This page is built by pulling feed from our own CMS and creating a list of synopses with links to the details
  So the main risks are that list items are malformed. Therefore, verifying syntactic structure goes a long way to
  determining that the page build was successful

  Scenario: HTML Compliance with W3C standards
    Given the w3C HTML tester reviews the file "bestiary"
    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
    Given the w3c link checker reviews the file "bestiary"
    Then the w3c link checker reports compliance

