@aut_Mykhailo_Shpilienko
Feature: Persistent storage in database
  As a developer,
  I want web app automatically updates database tables,
  So that one-to-many relationship is maintained.

  Background: 
    Given I have launched the application
    And I logged in as admin
    And I am on product management page

  @e2e @regression
  Scenario: One-to-many relationship between Category and Products
    Given I add new category as "TestDbCategory" with description as "Description"
    And I add new products
      | Category       | Name          | Brand       | Description           | UnitPrice | Qty | Image        |
      | TestDbCategory | Testmedicine1 | TestbrandDb | This is test medicine |      31.0 | 100 | medicine.png |
      | TestDbCategory | Testmedicine2 | TestbrandDb | This is test medicine |     54.00 |  99 | medicine.png |
      | TestDbCategory | Testmedicine3 | TestbrandDb | This is test medicine |        61 |  88 | medicine.png |
    When I connect to database
    And I trigger SELECT query to database tables "product" and "category"
    Then I should see field "id" in category table has relation one-to-many with field "category_id" in product table
