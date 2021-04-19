@tag
  Feature: Shopify app checkout

    Scenario: client makes call to GET all products
      When  client calls /myshopify/allproducts
      Then the client receives the response object size of 3

    Scenario: retrieve product by code
      When client searches for a product using product code LPTP
      Then the service will return the product: 'HP laptop'

    Scenario: purchase one or more of these products by providing their unique ID.
      Given customer 1 has 20 active days
      When customer request to purchase product item LPTP and SNKR
      Then Client receives status '200 OK'
