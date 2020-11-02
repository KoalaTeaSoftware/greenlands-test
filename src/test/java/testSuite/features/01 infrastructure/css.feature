@standards
Feature: Infrastructure: CSS Standards Compliance
  CSS Syntax errors could be a reason for a page looking bad. Selenium is not good for determining if a page looks good,
  so checking the syntax of the file gives some confidence that there has been no regression since the last human inspection.

  Scenario Outline: CSS Compliance with W3C standards
    Given the w3C CSS tester reviews the file "<file>"
    Then the w3c CSS tester reports compliance
    Examples:
      | file               |
      | /styles/styles.css |


