@functional
Feature: Widget: Instagram Feed
  This feed provides the four latest images from Rose's Greenlands Instagram
  This is provided from "https://the-greenlands.com/blog/category/eric/feed/"
  Currently the results can be seen on the Arts Page

  Background:
    Given I navigate to the page "art"

  Scenario: Review Instagram images
  The nice people at Facebook make getting the images any other way that through Instagram into a drama
  The following ensures that the mechanism that is used on the sight is up to date for them
    Then there are 4 instagram images
    And the instagram images are fully drawn

  Scenario: Follow the individual Instagram links
  Each image should take you to an instagram page.
  There is no sensible way of embedding the information into this Gherkin, so roll it all into one step
  This will have to click on a link and see that the new tab has a good title
  'Good' will have to be defined when the test step is being built
    Then all the individual instagram image links get the expected page

  Scenario: follow the follow me button
  This should always take you to the Instagram home page, in a new tab
    When I follow the follow me on instagram CTA
    Then the new page title is good
