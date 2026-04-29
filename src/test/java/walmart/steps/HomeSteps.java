package walmart.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import walmart.context.TestContext;
import walmart.pages.HomePage;

public class HomeSteps {

    private final TestContext context;

    public HomeSteps(TestContext context) {
        this.context = context;
    }

    @Given("the user navigates to the home page")
    public void theUserNavigatesToTheHomePage() {
        context.pages().homePage().openHomePage();
    }

    @Then("the main home page components should be visible")
    public void theMainHomePageComponentsShouldBeVisible() {
        HomePage homePage = context.pages().homePage();

        Assert.assertTrue(homePage.isLogoVisible(), "Logo is not visible");
        Assert.assertTrue(homePage.isSearchInputVisible(), "Search input is not visible");
        Assert.assertTrue(homePage.isSearchButtonVisible(), "Search button is not visible");
        Assert.assertTrue(homePage.isCartButtonVisible(), "Cart button is not visible");
        Assert.assertTrue(homePage.isCartTotalVisible(), "Cart total is not visible");
        Assert.assertTrue(homePage.isNavbarVisible(), "Navbar is not visible");
        Assert.assertTrue(homePage.isHeroSwiperVisible(), "Hero swiper is not visible or has no images");
        Assert.assertTrue(homePage.areProductCardsVisible(), "Product cards are not visible");
        Assert.assertTrue(homePage.isBrandsSwiperVisible(), "Brands swiper is not visible or has no images");
        Assert.assertTrue(homePage.isFooterVisible(), "Footer is not visible");
    }
}
