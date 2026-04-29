Feature: Shopping Cart Flow

  @smoke @cart @featured
  Scenario: Add product from Featured and validate Shopping Cart
    Given the user navigates to the home page
    When the user adds "MacBook" from Featured products to the cart
    Then the cart item count should increase by 1
    And a success message should be displayed
    When the user opens the cart and navigates to the Shopping Cart page
    Then the Shopping Cart page should be displayed
    And the product "MacBook" should be visible in the cart
    And the quantity field for "MacBook" should be displayed
    And the Continue Shopping button should be displayed
    And the Checkout button should be displayed

  @regression @cart @featured
  Scenario: Update product quantity and validate Shopping Cart totals
    Given the user navigates to the home page
    When the user adds "MacBook" from Featured products to the cart
    And the user opens the cart and navigates to the Shopping Cart page
    Then the Shopping Cart page should be displayed
    And the Shopping Cart product table columns should be displayed
    And the product "MacBook" should be visible in the cart
    And the quantity field for "MacBook" should be editable
    When the user updates the quantity of "MacBook" to "2"
    Then the row total for "MacBook" should be recalculated correctly

  @regression @cart @search
  Scenario: Add product from search and validate Shopping Cart
    Given the user navigates to the home page
    When the user searches for "Iphone"
    Then the search results page should be displayed
    And products related to "Iphone" should be displayed
    When the user opens the product "Iphone" from search results
    Then the product details page for "Iphone" should be displayed
    When the user adds the product to the cart from the product details page
    Then the cart item count should increase by 1
    And a success message should be displayed
    When the user navigates to the Shopping Cart page from the top menu
    Then the Shopping Cart page should be displayed
    And the searched product should be visible in the cart
