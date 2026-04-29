package walmart.steps;

import io.cucumber.java.en.Given;
import org.testng.Assert;
import walmart.context.TestContext;
import walmart.model.ProductData;
import walmart.pages.CartPage;
import walmart.pages.HomePage;
import walmart.pages.ProductPage;
import walmart.pages.SearchResultsPage;
import walmart.utils.CsvReader;

public class CommonSteps {

    private final TestContext context;

    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @Given("the user has product {string} in the cart")
    public void theUserHasProductInTheCart(String productKey) {
        HomePage homePage = context.pages().homePage();
        SearchResultsPage searchResultsPage = context.pages().searchResultsPage();
        ProductPage productPage = context.pages().productPage();
        CartPage cartPage = context.pages().cartPage();

        ProductData productData = CsvReader.getProductData(productKey);

        context.setSearchTerm(productData.searchTerm());
        context.setExpectedProductName(productData.productName());

        homePage.openHomePage();
        homePage.searchProduct(context.getSearchTerm());

        Assert.assertTrue(searchResultsPage.isResultsDisplayed(),
                "Search results were not displayed for: " + context.getSearchTerm());

        searchResultsPage.openProduct(context.getExpectedProductName());

        Assert.assertTrue(productPage.isProductPageDisplayed(),
                "Product details page was not displayed for: " + context.getExpectedProductName());

        productPage.addToCart();

        homePage.waitUntilCartHasAtLeastOneItem();
        homePage.waitUntilSuccessMessageIsDisplayed();

        cartPage.openCartPageFromTopMenu();

        Assert.assertTrue(cartPage.isShoppingCartPageDisplayed(),
                "Shopping Cart page was not displayed");

        Assert.assertTrue(cartPage.getProductName().contains(context.getExpectedProductName()),
                "Expected product was not visible in cart. Expected: "
                        + context.getExpectedProductName()
                        + " Actual: "
                        + cartPage.getProductName());
    }
}
