package walmart.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import walmart.context.TestContext;
import walmart.pages.HomePage;
import walmart.model.ProductData;
import walmart.pages.ProductPage;
import walmart.pages.SearchResultsPage;
import walmart.utils.CsvReader;

public class SearchSteps {

    private final TestContext context;

    public SearchSteps(TestContext context) {
        this.context = context;
    }

    @When("the user searches for {string}")
    public void theUserSearchesFor(String productKey) {
        ProductData productData = CsvReader.getProductData(productKey);

        context.setSearchTerm(productData.searchTerm());
        context.setExpectedProductName(productData.productName());

        context.pages().homePage().searchProduct(context.getSearchTerm());
    }

    @Then("products related to {string} should be displayed")
    public void productsRelatedShouldBeDisplayed(String productKey) {
        SearchResultsPage searchResultsPage = context.pages().searchResultsPage();

        Assert.assertTrue(searchResultsPage.isResultsDisplayed(),
                "Search results are not displayed for: " + productKey);
    }

    @Then("the search results page should be displayed")
    public void theSearchResultsPageShouldBeDisplayed() {
        SearchResultsPage searchResultsPage = context.pages().searchResultsPage();

        Assert.assertTrue(
                searchResultsPage.isSearchResultsPageDisplayed(),
                "Search Results page is not displayed"
        );
    }

    @When("the user opens the product {string} from search results")
    public void theUserOpensProductFromSearchResults(String productKey) {
        SearchResultsPage searchResultsPage = context.pages().searchResultsPage();

        searchResultsPage.openProduct(context.getExpectedProductName());
    }

    @Then("the product details page for {string} should be displayed")
    public void theProductDetailsPageShouldBeDisplayed(String productKey) {
        ProductPage productPage = context.pages().productPage();

        Assert.assertTrue(productPage.isProductPageDisplayed(),
                "Product details page is not displayed for: " + context.getExpectedProductName());
    }

    @When("the user adds the product to the cart from the product details page")
    public void theUserAddsProductToCartFromDetailsPage() {
        HomePage homePage = context.pages().homePage();
        int cartItemCountBefore = homePage.getCartItemCount();

        context.setExpectedCartItemCount(cartItemCountBefore + 1);
        context.pages().productPage().addToCart();
        homePage.waitUntilCartItemCountIs(context.getExpectedCartItemCount());
    }
}
