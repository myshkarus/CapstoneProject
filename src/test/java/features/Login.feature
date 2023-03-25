@aut_Mykhailo_Shpilienko
Feature: Login
  As a user,
  I want to login to web app using my credentials,
  So that I can securely order medicines and pay online.
  
  As an admin,
  I want to login to admin portal using my credentials,
  So that I can manage products in web app.  
  

  Background: 
    Given I have launched the application
    And I go to Login page

  @e2e
  Scenario: Successful login with valid credentials
    Given I am end-user
    When I enter valid email and password
    And I click on Login button
    Then I should see home page and my name in navigate menu

  @e2e
  Scenario: Access to admin portal
  	Given I am an admin
    When I enter valid email and password
    And I click on Login button
    Then I should see "Manage Product" option in admin portal
    
  @regression
  Scenario Outline: Failure to login with invalid credentials
    When I enter invalid email as "<Email>", password as "<Password>"
    And I click on Login button
    Then I should get the error message "Username and Password is invalid!"

    Examples: 
      | Email          | Password |
      | abc@xyz.com    | abcd123  |
      | cba@domain.com | 1234xyz  |
