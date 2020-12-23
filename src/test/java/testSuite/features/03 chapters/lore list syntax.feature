@functional
Feature: Chapter: Lore: List Page: Syntax
  This page give a listing summary of all of the lore posts in the chapter
  This page is built by pulling feed from our own CMS and creating a list of synopses with links to the details
  So the main risks are that list items are malformed. Therefore, verifying syntactic structure goes a long way to
  determining that the page build was successful

  @regression
  Scenario: HTML syntax veracity
    # if the request of data from the CMS fails, it is likely that the HTML will be invalid
    Given the w3C HTML tester reviews the file "lore"
    Then the w3c HTML tester reports compliance

# Checking the links may become impractical when the list gets long
  Scenario: Check links on a page
    Given the w3c link checker reviews the file "lore"
    Then the w3c link checker reports compliance
      | exceptions        |
      | www.instagram.com |
