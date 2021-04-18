@tag
  Feature: Shopify app checkout

    @tag1
    Scenario: Get all registered customers
      Given Execute
      When  GET all call
      Then show all customers stored

