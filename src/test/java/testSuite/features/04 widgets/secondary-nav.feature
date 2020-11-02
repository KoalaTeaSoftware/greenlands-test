@functional
Feature: Widget: Secondary Nav
  This widget is a nav bar at the bottom of all pages
  It provides various features that the user may be interested in

  Scenario Outline: Check the range of secondary links
  Take a few pages as a 'sample', and assume that all pages will display this range
    When I navigate to the page "<startPoint>"
    Then the secondary nav links are present
    Examples:
      | startPoint |
      |            |
      | bestiary   |

  Scenario Outline: Follow links within the secondary nave and see where they go
  Take a few pages as a 'sample', and assume that all pages will display this range
  Note that the membership does not result in a page change
    Given I navigate to the page "<startPoint>"
    And the page title is "<startingTitle>"
    When I follow the secondary nav with text "<buttonText>"
    Then the page title is "<expectedTitle>"
    Examples:
      | startPoint | startingTitle              | buttonText | expectedTitle              |
      |            | The Greenlands             | Contact    | The Greenlands \| contact  |
      |            | The Greenlands             | Policies   | The Greenlands \| policies |
      |            | The Greenlands             | Membership | The Greenlands             |
      | bestiary   | The Greenlands \| bestiary | Contact    | The Greenlands \| contact  |
      | bestiary   | The Greenlands \| bestiary | Policies   | The Greenlands \| policies |
      | bestiary   | The Greenlands \| bestiary | Membership | The Greenlands \| bestiary |

  Scenario Outline: Check membership link
  This link differs from the others in that it invokes a popup, and does not changing the page
    Given I navigate to the page "<startPoint>"
    And the membership widget is not visible
    When I follow the secondary nav with text "Membership"
    Then the membership widget is visible
    Examples:
      | startPoint |
      |            |
      | bestiary   |
