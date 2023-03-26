@aut_Mykhailo_Shpilienko
Feature: Get products via API
  As a developer,
  I want to fetch products via API,
  So that I do not have to manually search for the most viewed or most purchased products.

  Background: 
    Given base URL is "http://localhost:8080/medicare"

  @e2e
  Scenario: Retrieve the most viewed products
    Given the API endpoint is "/json/data/mv/products"
    When I hit endpoint with GET request
    Then I should see response status code 200
    And The list of "name" of the most viewed products is the same as on Home page of web application

  @e2e
  Scenario: Retrieve the most purchased products
    Given the API endpoint is "/json/data/mp/products"
    When I hit endpoint with GET request
    Then I should see response status code 200
    And cookie is "JSESSIONID"
    And The list of "name" of the most purchased products is the same as on Home page of web application
