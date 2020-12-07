@functional
Feature: Chapter: Home
  This is the landing page for the site. It provide chapter navigation.
  There are various common elements, but these are their own feature

  Scenario: Observe the page
    Given I navigate to the page ""
    Then the page title is "The Greenlands"

  Scenario: HTML Compliance with W3C standards
    Given the w3C HTML tester reviews the file ""
    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
    Given the w3c link checker reviews the file ""
    Then the w3c link checker reports compliance

  Scenario Outline: Follow chapter links and see that we get the right chapter
  The home page is, basically, a graphical chapter navigator
  This testing differs from the 'broken links' testing in that it demonstrates that the links in the nav bar actually take you
  to where you want to be taken.
    Given I navigate to the page ""
    When I go to the chapter called "<linkText>"
    Then the page title is "<expectedPageTitle>"
    Examples:
      | linkText | expectedPageTitle |
      | Bestiary | Bestiary          |
      | Roots    | Roots             |
      | Music    | Music             |
      | Stories  | Stories           |
      | Potions  | Potions           |
      | Lore     | Lore              |
