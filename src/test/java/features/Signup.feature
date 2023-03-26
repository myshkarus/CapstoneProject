@aut_Mykhailo_Shpilienko
Feature: Sign Up
  As a user,
  I want to submit my personal data,
  So that I am registered on web app as an end-user or admin.

  Background: 
    Given I have launched the application
    And I go to Sign up page

  @e2e @regression
  Scenario Outline: Successful registration on web app
    Given I have a role as "<Role>"
    When I enter my personal data
    And I enter my address
    And I submit my data
    And I see "Welcome!" message on web app and click Login
    And I enter my credentials on Login form
    And I click on Login button on Login form
    Then I should see my name in navigate menu
    And I can find my personal data and address in database

    Examples: 
      | Role     |
      | user     |
      | supplier |

  @regression
  Scenario Outline: Failure to register with incomplete personal data
    Given I am a user
    When I missed to enter my "<FirstName>" or "<LastName>"
    Then I should see "<Warning>" message under corresponding field

    Examples: 
      | FirstName | LastName | Warning                  |
      |           | Smith    | Please enter first name! |
      | Adam      |          | Please enter last name!  |
