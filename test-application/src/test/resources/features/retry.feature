Feature: rabbit message retry with delay

  Scenario: Should retry rabbit message with delay
    When message "fail" is sent
    Then status code 200 returned
    When message "ok" is sent
    Then status code 200 returned
    When message "ok" is sent
    Then status code 200 returned
    And wait "3" seconds
    When request messages count
    Then response should contain
      | field | fieldValue |
      | fail  | 3          |
      | ok    | 2          |

