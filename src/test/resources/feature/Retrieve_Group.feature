Feature: Retrieve Groups

  @PositiveTest
  Scenario: To Retrieve a specific Group details
    Given I execute GET url to Retrieve a Group details
    Then the response status code should be 200
    And I execute and verify Retrieved Group details with below values:
      | guid | Bm4166jJLxX |
      | name | bhagappa    |
      | role | org-admin   |

  @NegativeTest
  Scenario: Retrieve Groups - Invalid group_guid - Error code 403 Forbidden
    Given I execute GET method with invalid group_guid
    Then the response status code should be 403
    And I assert the response of invalid group_guid with below values:
      | message  | FORBIDDEN |
      | resource | groups    |

  @NegativeTest
  Scenario: Retrieve Groups - blank/null group_guid - Error code 403 Forbidden
    Given I execute GET method with invalid group_guid
    Then the response status code should be 403
    And I assert the response of invalid group_guid with below values:
      | message  | FORBIDDEN |
      | resource | groups    |













