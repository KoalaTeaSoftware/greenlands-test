@functional
Feature: Chapter: Bestiary: List Page: Contents
  This set of tests look in a bit more depth at the items on the page

  Background: Get us to that chapter
    Given I navigate to the page "bestiary"
    And the page title is "Bestiary"

  Scenario: Count beast list items
    Then there are more than 7 beasts listed

  @regression
  Scenario: Review the images in the list
    Given I navigate to the page "bestiary"
    Then all images are well formed

  Scenario: Review each list entry
  In the interest of efficiency, this combines a handful of rules about the content of the list entries
  Aside from the size criteria, there is a rule about the presence of a video
    Then all of the beast entries are well formed
      | minHeaderSize    | 3  |
      | minTextSize      | 10 |
      | minCaptionLength | 5  |
      | minNumberImages  | 1  |
      | maxNumberImages  | 3  |

  @regression
  Scenario Outline: Follow some details links
  This follows a sample, the implication is that if some work, they all work.
  This is not actually the case, as the links depend on the correct logging of the entry in the CMS
  However, this test shows that the site's code is correctly operational
    When I follow a details link for item entitled "<linkText>"
    Then the page title is "<expectedTitle>"
    And the first heading is "<linkText>"
    Examples:
      | linkText   | expectedTitle |
      | The Goblin | The Goblin    |
      | The Elf    | The Elf       |

  @wip @cms-data-entry
  Scenario: Follow all links
  As indicated in the 'follow some links' scenario, there is a risk that invalid data entry into the CMS can cause
  this page to show links that do not actually take the user to the right page.
  This is risk that emerges from the implementation decision to use WP in the way that it is being used.
  This is not something that should prevent deployment of the site's code, so this scenario, when written, should
  not be part of the CD/CD gateway, but part of a regular check on the data in the CMS
    Given I follow each and all links on the bestiary list
    Then the page title is as expected


