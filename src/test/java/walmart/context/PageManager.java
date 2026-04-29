package walmart.context;

import walmart.factory.DriverFactory;
import walmart.pages.CartPage;
import walmart.pages.CheckoutPage;
import walmart.pages.HomePage;
import walmart.pages.ProductPage;
import walmart.pages.SearchResultsPage;

public class PageManager {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    public HomePage homePage() {
        if (homePage == null) {
            homePage = new HomePage(DriverFactory.getDriver());
        }
        return homePage;
    }

    public SearchResultsPage searchResultsPage() {
        if (searchResultsPage == null) {
            searchResultsPage = new SearchResultsPage(DriverFactory.getDriver());
        }
        return searchResultsPage;
    }

    public ProductPage productPage() {
        if (productPage == null) {
            productPage = new ProductPage(DriverFactory.getDriver());
        }
        return productPage;
    }

    public CartPage cartPage() {
        if (cartPage == null) {
            cartPage = new CartPage(DriverFactory.getDriver());
        }
        return cartPage;
    }

    public CheckoutPage checkoutPage() {
        if (checkoutPage == null) {
            checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        }
        return checkoutPage;
    }
}
