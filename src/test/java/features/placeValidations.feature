Feature: Validating Place API

  @Regression
  @AddPlace
  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<address>" "<language>"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the APIs call get success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify placeID created map to "<name>" using "GetPlaceAPI"

    Examples:
      | name       | address              | language |
      | ColdHouse  | WildWestCenter       | English |
#      | SweetHouse | WorldCrossroadCenter | Russian |

  @Regression
  @DeletePlace
  Scenario: Verify if Delete Place functionality is working
    Given Delete Place Payload
    When user calls "DeletePlaceAPI" with "POST" http request
    Then the APIs call get success with status code 200
    And "status" in response body is "OK"
