@function
Feature: Home Page
  As a visitor
  So that I can get around the site
  I want to see the chapters

  Background:
    Given I use browser "" to view the page at "http://stage.the-greenlands.com"

  Scenario: See the various components of the home page
    Then the title tag contains "The Greenlands"
    And the secondary nav links are present
    And the chapter images are present

