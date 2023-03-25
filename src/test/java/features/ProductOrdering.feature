@aut_Mykhailo_Shpilienko
Feature: Product ordering
  As a user,
  I want to create an order and pay for it online,
  So that I see order summary with details of the order.

  Background: 
    Given I have launched the application
    And I have account registered on web app
    And I logged in as user
    And I am on Catalog page

  @e2e @regression
  Scenario: Product ordering
    Given I put products to the cart
      | Combiflame  |
      | Amoxicillin |
    And I change product quantity
      | product     | qty |
      | Combiflame  |   2 |
      | Amoxicillin |   3 |
    And I choose shipping address on Checkout page
    When I enter credit card data
    And I click Pay button
    Then I should see Order summary with products name, their price, and quantity
      | product     | qty | price |
      | Combiflame  |   2 |    22 |
      | Amoxicillin |   3 |    54 |
    And I should see Total price of products
      | product     | total |
      | Combiflame  |    44 |
      | Amoxicillin |   162 |
    And I should see product availability is reduced by quantity ordered
      | product     | qty |
      | Combiflame  |   2 |
      | Amoxicillin |   3 |
