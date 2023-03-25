@aut_Mykhailo_Shpilienko
Feature: Add product
  As an admin,
  I want to add new medicine in admin portal,
  So that medicine is stored in database.

  Background: 
    Given I have launched the application
    And I logged in as admin

  @e2e @regression 
  Scenario: Adding new category
    Given I am on product management page
    When I click Add New Category button
    And I enter new category name as "Test category" and category description as "This is cucumber test product category"
    And I save new category
    Then I should see new category in Categories list on home page

  @e2e @regression
  Scenario Outline: Adding new product
    Given I am on product management page
    And There is a product category "Test category" with description as "This is cucumber test product category"
    And I enter new product with a name as "<Name>", brand as "<Brand>", description as "<Description>", unit price as "<UnitPrice>" and quantity as <Qty>
    And I upload a product image as "<Image>"
    And I select product category as "Test category"
    And I save new product
    Then I should see product in list of available product
    And product should be stored in database

    Examples: 
      | Name         | Brand        | Description              | UnitPrice | Qty | Image           |
      | Testalgin    | Testbrand    | This is test medicine    |      33.0 |  55 | medicine.png    |
      | Testabiotics | Testbrand    | This is test antibiotics |     51.00 |  38 | antibiotics.jpg |
      | Testastiride | Testnewbrand | This is test drug        |        28 |  60 | steroids.jpeg   |
