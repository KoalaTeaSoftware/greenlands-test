@functional
Feature: Chapter: Bestiary: Detail Page
  This page gives details about one specific beast
  This page is built by pulling data from our own CMS and creating a list of synopses with links to the details
  So the main risks are that list items are malformed. Therefore, verifying syntactic structure goes a long way to
  determining that the page build was successful
  This is separated from the list contents just because the others make use of the Background Gherkin construction and this does not

  Scenario: HTML syntax veracity
  if the request of data from the CMS fails, it is likely that the HTML will be invalid
    Given the w3C HTML tester reviews the file "bestiary/the-mermaid"
    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
  There are few links on the page (unless entered into the text using the CMS), only the secondary nav are to be expected
    Given the w3c link checker reviews the file "bestiary/the-mermaid"
    Then the w3c link checker reports compliance
      | exceptions  |
      | addthis.com |

  Scenario: review the title
    Given I navigate to the page "bestiary/the-mermaid"
    Then the page title is "The Mermaid"

  Scenario: Review images
    Given I navigate to the page "bestiary/the-troll"
    Then all images are well formed