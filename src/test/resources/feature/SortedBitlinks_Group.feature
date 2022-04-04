Feature: Retrieve Sorted Bitlinks for Group

  @PositiveTest
  Scenario: Returns a list of Bitlinks by group
    Given I set end point url to Retrieve Bitlinks for Group
    Then the response status code should be 200
    And I verify Retrieved Sorted Bitlinks response with below values:
      | created_by | bhagappa                                 |
      | client_id  | ece654beaf35f9c29f610ffd4fb128702b4bad15 |

  @PositiveTest
  Scenario: Returns a list of Bitlinks sorted by group
    Given I execute GET method to Retrieve Sorted Bitlinks for Group
    Then the response status code should be 200
    And I assert the response with values on Retrieved Sorted Bitlinks

  Scenario Outline: Retrieve Sorted Bitlinks for Group - Invalid or blank/null or Special chars group_guid - Error code 403 Forbidden
    Given I set end point url to "<group_guid_val>" group_guid to Retrieve Sorted Bitlinks
    Then the response status code should be 403
    And I assert the invalid group_guid response with below values:
      | message  | FORBIDDEN |
      | resource | bitlinks  |

    @NegativeTest
    Examples:
      | group_guid_val |
      | test1321       |
      | null           |
      | $234@test5     |

  @NegativeTest
  Scenario: Retrieve Sorted Bitlinks for Group - Invalid values in sorting - Error code 400 Bad Request
    Given I set GET end point url with invalid values in sorting
    Then the response status code should be 400
    And I assert the response of invalid values in sorting with below values:
      | message  | FORBIDDEN |
      | resource | bitlinks  |