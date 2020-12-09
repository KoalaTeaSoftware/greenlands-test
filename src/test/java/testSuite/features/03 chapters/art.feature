@functional @wip
Feature: Chapter: Art
  This page contains a few widget which have their own feature specification

  Scenario: HTML syntax validated
    Given the w3C HTML tester reviews the file "art"
    Then the w3c HTML tester reports compliance

# This is not a good test. It takes roughly 4 minutes and gives false negatives about Instagram and other integrations
  # The link risks are checked by the specific widget tests (for the widgets)
  # maybe a nav test could be added, just to check that the nav bar works on this page, but the risk of this failing is low

#  Scenario: Check links on a page
#    Given the w3c link checker reviews the file "art"
#    Then the w3c link checker reports compliance
      | exceptions        |
      | www.instagram.com |

