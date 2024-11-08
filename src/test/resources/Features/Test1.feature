Feature: Registration
  Background: Launch the application url
    Given I launch the browser

  @VerifyAPIDocumentation
  Scenario: Verify API Documentation
    Given I am on the "Automation Exercise" homepage
    When I click on the "Products" link
    Then I capture all the Product details
    When I click on the "API Testing" link
    Then I click on the "API2" from the list and validate the response of "Post" request
    Then I click on the "API4" from the list and validate the response of "Put" request
    Then I click on the "API9" from the list and validate the response of "Delete" request
    Then I click on the "API1" from the list and validate the response of "Get" request
    Then I click on the "API6" from the list and validate the response of "Post" request

    @VerifyHomePageElements
    Scenario: Verify home page elements
      Given I am on the "Automation Exercise" homepage
      Then I should see "Home" option
      Then I should see "Products" option
      Then I should see "Cart" option
      Then I should see "Signup / Login" option
      Then I should see "Test Cases" option
      Then I should see "API Testing" option
      Then I should see "Video Tutorials" option
      Then I should see "Contact us" option
      Then I should see 3 slides in the "Carousel"
      Then I should see 3 "Categories" in "Category" section
      Then I should see 8 "Brand Logos" in "Brand" section
      Then I should see 34 "Featured Items" in "Featured Items" section
      Then I should see "Subscription Email" textbox
      Then I should see "Subscription Submit" button

    @VerifyShoppingCartFlow
    Scenario: Verify shopping cart flow
      Given I am on the "Automation Exercise" homepage
      Then I navigate to "Men" Category and choose "T-shirts" Subcategory
      Then I should see "Pure Cotton Neon Green Tshirt" under products
      Then I verify the product details
      Then I hover over the product
      Then I add "Pure Cotton Neon Green Tshirt" to cart
      Then I click on "Continue Shopping" button
      Then I add "Pure Cotton V-Neck T-Shirt" to cart
      Then I click on "View Cart" button
      Then I verify the products in cart
      Then I click on "Proceed to Checkout" button







