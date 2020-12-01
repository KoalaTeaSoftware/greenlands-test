@functional
Feature: Chapter: Lore: List Page: Contents
  This set of tests look in a bit more depth at the items on the page

  Background: Get us to that chapter
    Given I navigate to the page "lore"
    And the page title is "The Greenlands | lore"

  Scenario: Review the images in the list
    Then all images are well formed

  Scenario: Review each list entry
  In the interest of efficiency, this combines a handful of rules about the content of the list entries
  Aside from the size criteria, there is a rule about the presence of a video
    Then all of the lore entries are well formed
      | minHeaderSize    | 3  |
      | minTextSize      | 10 |
      | minCaptionLength | 0  |
      | minNumberImages  | 1  |
      | maxNumberImages  | 1  |

  Scenario Outline: Follow some links
    When I follow a lore link for item entitled "<linkText>"
    Then the page title is "<expectedTitle>"
    And the first heading is "<linkText>"
    Examples:
      | linkText                           | expectedTitle                                                |
      | A Potted History of the Greenlands | The Greenlands \| lore \| a-potted-history-of-the-greenlands |

  @wip @cms-data-entry
  Scenario: Follow all links
  As indicated in the 'follow some links' scenario, there is a risk that invalid data entry into the CMS can cause
  this page to show links that do not actually take the user to the right page.
  This is risk that emerges from the implementation decision to use WP in the way that it is being used.
  This is not something that should prevent deployment of the site's code, so this scenario, when written, should
  not be part of the CD/CD gateway, but part of a regular check on the data in the CMS
    Given I follow each and all links on the bestiary list
    Then the page title is as expected
