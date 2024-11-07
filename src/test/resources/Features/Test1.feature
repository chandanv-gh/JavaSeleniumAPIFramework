Feature: Registration
  Background: Launch the application url
    Given I launch the browser

  @VerifyAPIDocumentation
  Scenario: Verify API Documentation
    Given I am on the "Automation Exercise" homepage
    When I click on the "API Testing" link
    Then I click on the "API2" from the list and validate the response of "Post" request
    Then I click on the "API4" from the list and validate the response of "Put" request
    Then I click on the "API9" from the list and validate the response of "Delete" request
    Then I click on the "API1" from the list and validate the response of "Get" request
    Then I click on the "API6" from the list and validate the response of "Post" request

  @RegistrationWithBlankFields
  Scenario: Registration with missing fields
    Given I am on the "Parabank" homepage
    When I click on the "Register" link
    And I leave "Username" blank
    And I fill in "Password" with "Password123"
    When I click the "Register" button
    Then I should see an error message "Username is required."