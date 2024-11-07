Feature: Registration and Login
  Background: Launch the application url
    Given I launch the browser


  @SuccessfulLogin
  Scenario: Successful login
    Given I am on the "Parabank" homepage
    When I enter "testData" as the "Username"
    And I enter "testData" as the "Password"
    And I click the "Log In" button
    Then I should see a welcome message "Welcome TestAbc TestXyz"

  @LoginWithEmptyFields
  Scenario: Unsuccessful login with empty fields
    Given I am on the "Parabank" homepage
    When I leave "username" blank
    And I leave "password" blank
    And I click the "Log In" button
    Then I should see an error message "Please enter a username and password."

  @Home
  Scenario: Validate all the elements in the homepage
    Given I am on the "Parabank" homepage
#  testData.json with given page contains all the elements which need to be present in the webpage
    And I validate all the elements in the "Home" page