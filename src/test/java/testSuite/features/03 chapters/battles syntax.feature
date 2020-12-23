@functional
Feature: Chapter: Battles: Main Page Syntax
  This page is built in two parts, both from reading the CMS.
  Like the other CMS integration risks, the most likely risk is that the posts have not been properly entered.
  In which case, you can expect that the syntax of the page is bad, and that the images are not well formed

  @new
  Scenario: HTML syntax veracity
    # if the request of data from the CMS fails, it is likely that the HTML will be invalid
    Given the w3C HTML tester reviews the file "battles"
    Then the w3c HTML tester reports compliance

  @new
  Scenario: Check links on a page
    Given the w3c link checker reviews the file "Battles"
    Then the w3c link checker reports compliance
      | exceptions |

  @new @regression
  # included in regression as it is fairly quick and diagnostic of an error
  Scenario: Review images
    Given I navigate to the page "battles"
    Then the page title becomes "battles"
    And all images are well formed