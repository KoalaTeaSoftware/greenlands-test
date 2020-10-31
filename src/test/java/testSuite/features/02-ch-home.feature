@functional
Feature: Chapter: Home
  This is the landing page for the site. It provide chapter navigation.
  There are various common elements, but these are their own feature

  Scenario: Observe the secondary nav (at the base of the page)
  If this is missing, then it is likely that there has been a problem drawing this page
  This may be verified elsewhere, but put it here so that this feature can be independent of other feature files
    Given I navigate to the page ""
    Then the secondary nav links are present

  Scenario: Observe the chapter images
  This is a minor risk as the image files are served up from a CDN provided through WordPress
    Given I navigate to the page ""
    Then the chapter images are present

  Scenario Outline: Follow chapter links and see that we get the right chapter
  The home page is, basically, a graphical chapter navigator
  This testing differs from the 'broken links' testing in that it demonstrates that the links in the nav bar actually take you
  to where you want to be taken.
    Given I navigate to the page "<url>"
    When I go to the chapter called "<linkText>"
    Then the page title is "<expectedPageTitle>"
    Examples:
      | url | linkText | expectedPageTitle          |
      |     | Bestiary | The Greenlands \| bestiary |
      |     | Roots    | The Greenlands \| roots    |
      |     | Art      | The Greenlands \| art      |
      |     | Music    | The Greenlands \| music    |
      |     | Stories  | The Greenlands \| stories  |
      |     | Medicine | The Greenlands \| medicine |

  Scenario: HTML Compliance with W3C standards
    Given the w3C HTML tester reviews the file ""
    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
    Given the w3c link checker reviews the file ""
    Then the w3c link checker reports compliance

