@wip
Feature: Podcast display
  This is a playable button for the latest podcast host (Podbean), and a few connections to other hosts

  Background: Go to the page on which we see the widget
    Given I navigate to the page "stories"

  Scenario: Count the number of podcast links
    Then there are 4 podcast host buttons

  Scenario Outline:
    Given I navigate to the page "stories"
    When I follow a podcast host link named "<buttonText>"
    Then the page title on tab 2 is "<newTitle>"
    Examples:
      | buttonText | newTitle |
      | Podbean    |          |
      | iTunes     |          |
      | rss        |          |
      | google     |          |