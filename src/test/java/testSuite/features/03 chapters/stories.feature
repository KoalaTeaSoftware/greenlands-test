@feature
Feature: Chapter: Stories
  The stories chapter has an inclusion from the podcast platform, and a competition
  Both are actually tested as widgets, but we need to see that the page is generally tidy

  Scenario: See that the page is complete
    Given I navigate to the page "stories"
    Then the page title is "The Greenlands | stories"
    And the secondary nav links are present

  Scenario: See that the images are correctly shown
  This is a minor risk as the image files are not even served up from a CDN provided through WordPress
    Given I navigate to the page "stories"
    Then all images are well drawn

  Scenario: HTML Compliance with W3C standards
    Given the w3C HTML tester reviews the file "stories"
    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
  There are links to various podcast platforms
  I lieu of actually testing that they take the user to the right place, check that they go somewhere
    Given the w3c link checker reviews the file "stories"
    Then the w3c link checker reports compliance

  @wip
  Scenario: Count the number ot podcast links
    Given I navigate to the page "stories"
    Then there are 4 podcasts buttons

  @wip
  Scenario Outline:
    Given I navigate to the page "stories"
    When I follow a podcast link named "buttonText"
    Then the page title is "<newTitle>"
    Examples:
      | buttonText | newTitle |
      | Podbean    |          |
      | iTunes     |          |
      | rss        |          |
      | google     |          |