@functional @smoke
Feature: Infrastructure: Friendly URLs
  The site allows the use of (and only really responds to) friendly URLs.

  Scenario Outline: Try silly addresses (in the address bar)
    Given I navigate to the page "<url>"
    Then the page title is "<expectedTitle>"
    Examples:
      | url             | expectedTitle |
      | /engine-trouble |               |

  Scenario Outline: Known good chapters
  This differs from the 'broken links' testing in that it demonstrates that the links in the nav bar actually take you
  to where you want to be taken.
  It is worth looking at a few pages, because relative links (if they are used) may work in some pages and not others.
  This may not be the fastest, or most elegant way of doing this, but it is simple.
    When I navigate to the page "<url>"
    Then the page title is "<expectedPageTitle>"
    Examples:
      | url      | expectedPageTitle          |
      | bestiary | The Greenlands \| bestiary |
      | roots    | The Greenlands \| roots    |
      | art      | The Greenlands \| art      |
      | music    | The Greenlands \| music    |
      | stories  | The Greenlands \| stories  |
      | medicine | The Greenlands \| potions  |
