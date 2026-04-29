@regression @checkout
Feature: Checkout Flow

  Scenario: Complete checkout as guest user
    Given the user navigates to the home page
    When the user searches for "macbook"
    And the user opens the product "macbook" from search results
    And the user adds the product to the cart from the product details page
    And the user navigates to the Shopping Cart page from the top menu
    And the user proceeds to checkout
    And the user selects Guest Checkout
    And the user completes the billing details form using "guestCheckout1"
    And the user selects Bank Transfer as payment method
    And the user accepts the terms and conditions
    And the user continues with the payment method
    And the user confirms the order
    Then the order should be placed successfully

  @existingUser
  Scenario: Complete checkout with existing user
    Given the user has product "iphone" in the cart
    When the user proceeds to checkout
    And the user logs in during checkout using "existingUser1"
    And the user continues with the existing billing address
    And the user continues with the existing delivery address
    And the user continues with the delivery method
    And the user selects Bank Transfer as payment method
    And the user accepts the terms and conditions
    And the user continues with the payment method
    And the user confirms the order
    Then the order should be placed successfully

  @registerUser
  Scenario: Complete checkout by registering a new user
    Given the user has product "macbook" in the cart
    When the user proceeds to checkout
    And the user selects Register Account during checkout
    And the user completes the registration details using "newUser1"
    And the user continues with the delivery method
    And the user selects Bank Transfer as payment method
    And the user accepts the terms and conditions
    And the user continues with the payment method
    And the user confirms the order
    Then the order should be placed successfully
