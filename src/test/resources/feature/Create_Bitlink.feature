Feature: Create a Bitlink

  @PositiveTest
  Scenario: Converts a long url to a Bitlink and sets additional parameters
    When I POST with request_options and save as response
    Then the response status code should be 200
    And I assert the response of Created Bitlink below values:
      | title | Bitly API Documentation |

  Scenario Outline: Create a Bitlink - long_url/group_guid/deeplinks attribute is removed from request body - Status Code 400 Bad Request
    When I POST request body without "<req_attribute>" attribute to create Bitlink
    Then the response status code should be 400

    @NegativeTest
    Examples:
      | req_attribute |
      | long_url      |
      | group_guid    |
      | deeplinks     |

  @NegativeTest
  Scenario: Create a Bitlink - junk values/special characters group_guid and long_url - Status Code 403 Forbidden
    When I POST request body with junk values/special characters in group_guid and long_url attribute
    Then the response status code should be 403
    And I assert the invalid group_guid response with below values:
      | message  | FORBIDDEN |
      | resource | bitlinks  |

  @NegativeTest
  Scenario: Create a Bitlink - Invalid group_guid and long_url - Status Code 403 Forbidden
    When I POST request body with junk values/special characters in group_guid and long_url attribute
    Then the response status code should be 403
    And I assert the invalid group_guid response with below values:
      | message  | FORBIDDEN |
      | resource | bitlinks  |


