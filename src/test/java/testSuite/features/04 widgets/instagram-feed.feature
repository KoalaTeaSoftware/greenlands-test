@functional
Feature: Widget: Instagram Feed
  This feed provides the four latest images from Rose's Greenlands Instagram
  This is provided from "https://the-greenlands.com/blog/category/eric/feed/"
  Currently the results can be seen on the Arts Page

  Background:
    Given I navigate to the page "stories"

  Scenario: Review Instagram images
  The nice people at Facebook make getting the images any other way that through Instagram into a drama
  The following ensures that the mechanism that is used on the sight is up to date for them
    Then there are 4 instagram images
    And the instagram images are fully drawn

  Scenario: Follow the individual Instagram links
  Each image should take you to an instagram page fo the greenlands.
  There is no sensible way of embedding the information into this Gherkin, so roll it all into one step
  This will have to click on a link and see that the new tab has a good title
  'Good' will have to be defined when the test step is being built
    Then all the individual instagram image links get a greenlands instagram page

  Scenario: follow the follow me button
  This should always take you to the Instagram home page, in a new tab
    When I follow the follow me on instagram CTA
    # ToDo: Randomly, the links do not go to a kosher page, so this is just going to verify that it is an Instagram page
    # and ignore if it is a Greenlands page, or not
    Then the new page is a greenlands instagram page
