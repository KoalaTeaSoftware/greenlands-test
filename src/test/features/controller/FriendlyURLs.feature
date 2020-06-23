@function
Feature: Friendly URLs
  The URLs of the site are in the form <domain>/<chapter>/<section>/<sub-section>
  So as to be bother user-friendly, and crawler-friendly

  Scenario Outline: Visit known chapters
    Given I use browser "" to view the page at "<urlFrag>"
    Then the title tag contains "<pageTitleContainsIgnoringCase>"
    And the first header contains "<pageHeading>"
    Examples:
      | urlFrag                             | pageTitleContainsIgnoringCase | pageHeading |
      | https://the-greenlands.com/         | the greenlands                | Home        |
      | https://the-greenlands.com/stories  | stories                       | Stories     |
      | https://the-greenlands.com/bestiary | bestiary                      | Bestiary    |
      | https://the-greenlands.com/roots    | roots                         | Roots       |
      | https://the-greenlands.com/art      | art                           | Art         |
      | https://the-greenlands.com/music    | music                         | Music       |
      | https://the-greenlands.com/medicine | medicine                      | Medicine    |

  Scenario Outline: Empty chapter
  An empty chapter element in an otherwise not-empty URL path
  means that the rest of the path is ignored
    Given I use browser "" to view the page at "<urlFrag>"
    Then the title tag contains "the greenlands"
    And the first header contains "home"
    Examples:
      | urlFrag                                  |
      | https://the-greenlands.com//The-Werewolf |
      | https://the-greenlands.com//             |


  Scenario Outline: Visit known chapters - case is ignored
    Given I use browser "" to view the page at "<urlFrag>"
    Then the title tag contains "<browserTabContainsIgnoringCase>"
    And the first header contains "<browserTabContainsIgnoringCase>"
    Examples:
      | urlFrag                                          | browserTabContainsIgnoringCase |
      | https://the-greenlands.com/Medicine              | medicine                       |
      | https://the-greenlands.com/BESTIARY              | bestiary                       |
      | https://the-greenlands.com/bestiarY/The-Werewolf | werewolf                       |

  Scenario Outline: Visit unknown chapters
  We want to know that the page is good, hence the checking that the title is nice
  also that we did find that there was a problem, hence seeing that there is some extra info added to the url
    Given I use browser "" to view the page at "<urlFrag>"
    Then the title tag contains "the greenlands"
    And the current url contains "Unknown-Chapter"
    And the first header contains "home"
    Examples:
      | urlFrag                             |
      | https://the-greenlands.com/Oranges  |
      | https://the-greenlands.com/wobblers |
      | https://the-greenlands.com/ro ots   |

  Scenario Outline: Visit know sections
    Given I use browser "" to view the page at "<urlFrag>"
    Then the title tag contains "<pageTitleContainsIgnoringCase>"
    And the first header contains "<pageTitleContainsIgnoringCase>"
    Examples:
      | urlFrag                                          | pageTitleContainsIgnoringCase |
      | https://the-greenlands.com/bestiary/the-werewolf | werewolf                      |
      | https://the-greenlands.com/bestiary/the-dwarf    | dwarf                         |

  Scenario Outline: Visit unknown sections in chapters that can have sections
  This is a known risk because each chapter has to handle deciding whether the section requested can be served
  If it is invalid, they will just draw the chapter page
    Given I use browser "" to view the page at "<urlFrag>"
    Then the title tag contains "<pageTitleContainsIgnoringCase>"
    And the first header contains "<pageTitleContainsIgnoringCase>"
    Examples:
      | urlFrag                                            | pageTitleContainsIgnoringCase |
      | https://the-greenlands.com/bestiary/the-politician | bestiary                      |
#      | https://the-greenlands.com/stories/long-ones       | stories                       |

  Scenario Outline: Visit unknown sections in chapters that have no sections
  This is a known risk because each chapter has to handle deciding whether the section requested can be served
  If it is invalid, they will just draw the chapter page
    Given I use browser "" to view the page at "<urlFrag>"
    Then the title tag contains "<pageTitleContainsIgnoringCase>"
    And the first header contains "<pageTitleContainsIgnoringCase>"
    Examples:
      | urlFrag                                   | pageTitleContainsIgnoringCase |
      | https://the-greenlands.com/roots/us       | roots                         |
      | https://the-greenlands.com/art/good-stuff | art                           |
      | https://the-greenlands.com/music/loud     | music                         |
      | https://the-greenlands.com/medicine/shop  | medicine                      |
