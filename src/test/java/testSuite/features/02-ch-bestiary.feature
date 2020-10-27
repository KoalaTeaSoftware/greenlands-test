@functional
Feature: Bestiary List - Specificities
  This set of tests look in a bit more depth at the items on the page

  Background: Get us to that chapter
    Given I navigate to the page "bestiary"
    And the page title is "The Greenlands | bestiary"

  Scenario: Count beast list items
    Then there are more than 7 beasts listed

  Scenario: Review each list entry
  In the interest of efficiency, this combines a handful of rules about the content of the list entries
  Aside from the size criteria, there is a rule about the presence of a video
    Then all of the beast entries are well formed
      | minHeaderSize    | 3  |
      | minTextSize      | 10 |
      | minCaptionLength | 5  |
      | minNumberImages  | 1  |
      | maxNumberImages  | 3  |

  Scenario Outline: Follow links
    When I follow a details link for item entitles "<linkText>"
    Then the page title is "<expectedTitle>"
    And the first heading is "<linkText>"
    Examples:
      | linkText   | expectedTitle                            |
      | The Goblin | The Greenlands \| bestiary \| the-goblin |
      | The Elf    | The Greenlands \| bestiary \| the-elf    |