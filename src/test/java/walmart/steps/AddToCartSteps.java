package walmart.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import walmart.context.TestContext;
import walmart.pages.CartPage;
import walmart.pages.HomePage;

public class AddToCartSteps {

    private final TestContext context;

    public AddToCartSteps(TestContext context) {
        this.context = context;
    }

    @When("the user adds {string} from Featured products to the cart")
    public void theUserAddsFromFeaturedProductsToTheCart(String productName) {
        HomePage homePage = context.pages().homePage();

        int cartItemCountBefore = homePage.getCartItemCount();
        int expectedCartItemCountAfter = cartItemCountBefore + 1;

        context.setExpectedCartItemCount(expectedCartItemCountAfter);

        homePage.addFeaturedProductToCart(productName);
        homePage.waitUntilCartItemCountIs(expectedCartItemCountAfter);
    }

    @Then("the cart item count should increase by 1")
    public void theCartItemCountShouldIncreaseByOne() {
        HomePage homePage = context.pages().homePage();

        homePage.waitUntilCartHasAtLeastOneItem();

        int actualCartItemCount = homePage.getCartItemCount();
        Integer expectedCartItemCount = context.getExpectedCartItemCount();

        Assert.assertNotNull(expectedCartItemCount,
                "Expected cart item count was not initialized before validating the cart counter");
        Assert.assertEquals(actualCartItemCount, expectedCartItemCount.intValue(),
                "Cart item count mismatch after adding product");
    }

    @Then("a success message should be displayed")
    public void aSuccessMessageShouldBeDisplayed() {
        HomePage homePage = context.pages().homePage();

        homePage.waitUntilSuccessMessageIsDisplayed();

        Assert.assertTrue(homePage.isSuccessMessageDisplayed(),
            "Success message was not displayed after adding product to cart");
    }

    @When("the user opens the cart and navigates to the Shopping Cart page")
    public void theUserOpensTheCartAndNavigatesToTheShoppingCartPage() {
        context.pages().cartPage().clickViewCart();
    }

    @When("the user navigates to the Shopping Cart page from the top menu")
    public void theUserNavigatesToTheShoppingCartPageFromTheTopMenu() {
        context.pages().cartPage().clickViewCart();
    }

    @Then("the Shopping Cart page should be displayed")
    public void theShoppingCartPageShouldBeDisplayed() {
        Assert.assertTrue(
                context.pages().cartPage().isShoppingCartPageDisplayed(),
                "Shopping Cart page is not displayed"
        );
    }

    @Then("the product {string} should be visible in the cart")
    public void theProductShouldBeVisibleInTheCart(String productName) {
        Assert.assertTrue(
                context.pages().cartPage().isProductVisibleInCart(productName),
                "Product is not visible in the Shopping Cart: " + productName
        );
    }

    @Then("the quantity field for {string} should be displayed")
    public void theQuantityFieldForProductShouldBeDisplayed(String productName) {
        Assert.assertTrue(
                context.pages().cartPage().isQuantityFieldDisplayedForProduct(productName),
                "Quantity field is not displayed for product: " + productName
        );
    }

    @Then("the Continue Shopping button should be displayed")
    public void theContinueShoppingButtonShouldBeDisplayed() {
        Assert.assertTrue(
                context.pages().cartPage().isContinueShoppingButtonDisplayed(),
                "Continue Shopping button is not displayed"
        );
    }

    @Then("the Checkout button should be displayed")
    public void theCheckoutButtonShouldBeDisplayed() {
        Assert.assertTrue(
                context.pages().cartPage().isCheckoutButtonDisplayed(),
                "Checkout button is not displayed"
        );
    }

    @Then("the Shopping Cart product table columns should be displayed")
    public void theShoppingCartProductTableColumnsShouldBeDisplayed() {
        Assert.assertTrue(
                context.pages().cartPage().areProductTableColumnsDisplayed(),
                "Shopping Cart product table columns are not displayed correctly"
        );
    }

    @Then("the quantity field for {string} should be editable")
    public void theQuantityFieldForProductShouldBeEditable(String productName) {
        Assert.assertTrue(
                context.pages().cartPage().isQuantityFieldEditableForProduct(productName),
                "Quantity field is not editable for product: " + productName
        );
    }

    @When("the user updates the quantity of {string} to {string}")
    public void theUserUpdatesTheQuantityOfProductTo(String productName, String quantity) {
        context.pages().cartPage().updateQuantityForProduct(productName, quantity);
    }

    @Then("the row total for {string} should be recalculated correctly")
    public void theRowTotalForProductShouldBeRecalculatedCorrectly(String productName) {
        Assert.assertTrue(
                context.pages().cartPage().isRowTotalRecalculatedCorrectly(productName),
                "Row total was not recalculated correctly for product: " + productName
        );
    }

    @Then("the searched product should be visible in the cart")
    public void theSearchedProductShouldBeVisibleInTheCart() {
        CartPage cartPage = context.pages().cartPage();
        String actualProductName = cartPage.getProductName();

        Assert.assertTrue(actualProductName.contains(context.getExpectedProductName()),
            "Expected product from CSV was not found in cart. Expected: "
                    + context.getExpectedProductName()
                    + " Actual: "
                    + actualProductName);
    }
}
