@feature
Feature: Chapter: Music

  @regression
  Scenario: See that the page is complete
    Given I navigate to the page "music"
    Then the page title is "Music"
    And all images are well formed

#  This page used Vue.js to build the page. The W3c Html validator does not like this
# ToDo:
#  Scenario: HTML syntax veracity
#  if the request of data from the CMS fails, it is likely that the HTML will be invalid
#    Given the w3C HTML tester reviews the file "music"
#    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
  There are one, or two links that are specific to the page
    Given the w3c link checker reviews the file "music"
    Then the w3c link checker reports compliance
      | exceptions      |
      | amazon-adsystem |
