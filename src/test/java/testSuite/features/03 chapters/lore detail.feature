@functional
Feature: Chapter: Lore: Detail Page
  This page gives  one specific lore story
  This page is built by pulling data from our own CMS and creating a list of synopses with links to the details
  So the main risks are that list items are malformed. Therefore, verifying syntactic structure goes a long way to
  determining that the page build was successful
  This is separated from the list contents just because the others make use of the Background Gherkin construction and this does not

  Scenario: HTML syntax veracity
  If the request of data from the CMS fails, it is likely that the HTML will be invalid
    Given the w3C HTML tester reviews the file "lore/a-potted-history-of-the-greenlands"
    Then the w3c HTML tester reports compliance

  Scenario: Check links on a page
  There are few links on the page (unless entered into the text using the CMS), only the secondary nav are to be expected
  This may fail with some error relating to the addthis widget. This does not appear to compromise it operation
    Given the w3c link checker reviews the file "lore/a-potted-history-of-the-greenlands"
    Then the w3c link checker reports compliance

  Scenario: Review the title
    Given I navigate to the page "lore/a-potted-history-of-the-greenlands"
    Then the page title is "The Greenlands | lore | a-potted-history-of-the-greenlands"

  Scenario: Review images
    Given I navigate to the page "lore/a-potted-history-of-the-greenlands"
    Then all images are well formed