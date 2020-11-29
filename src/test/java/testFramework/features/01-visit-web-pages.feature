@smoke
Feature: Visit Web Pages
  These scenarios smoke-test features of the test framework

  Scenario Outline: Visit a the default SUT page
  Thus checks the defaults set in the SUT configuration.
  koalateasoftware.com does not have any built in redirection to https. Other sites may give you false negatives
    When I navigate to the page "<address>"
    Then the page title is "<title>"
    And the page scheme is "<scheme>"
    Examples:
      | address                         | title                 | scheme |
      # this particular test suite is defaulting to RoseGoldthorp.com, so these all-default expectations have to as follows
      | /                               | The Greenlands        | http   |
      # The following values are for more helpfully testing the framework
      | koalateasoftware.com/           | Web Site Development  |        |
      | http://koalateasoftware.com     | Web Site Development  | http   |
      | https://koalateasoftware.com    | Web Site Development  | https  |
      # this one has a built-in redirect to https
      | http://swordsandclapboards.com/ | Swords and Clapboards | https  |


