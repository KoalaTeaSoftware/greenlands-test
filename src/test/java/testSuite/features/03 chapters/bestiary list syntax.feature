@functional
Feature: Chapter: Bestiary: List Page Syntax
  This page gives a listing summary of all of the beasts in the bestiary
  This page is built by pulling feed from our own CMS and creating a list of synopses with links to the details
  So the main risks are that list items are malformed. Therefore, verifying syntactic structure goes a long way to
  determining that the page build was successful
  This is separated from the list contents just because the others make use of the Background Gherkin construction and this does not

  Scenario: HTML syntax veracity
    # if the request of data from the CMS fails, it is likely that the HTML will be invalid
    Given the w3C HTML tester reviews the file "bestiary"
    Then the w3c HTML tester reports compliance

# Checking the links takes over 5 minutes using this method, so this is not practical
#  Scenario: Check links on a page
#    Given the w3c link checker reviews the file "bestiary"
#    Then the w3c link checker reports compliance
#      | exceptions        |
#      | www.instagram.com |