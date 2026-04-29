package walmart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import walmart.base.BasePage;
import walmart.config.TestConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomePage extends BasePage {

    private final String homeUrl = TestConfig.getBaseUrl() + "/";

    private final By logo = By.cssSelector("#logo a");
    private final By searchInput = By.cssSelector("#search input");
    private final By searchButton = By.cssSelector("#search button");
    private final By cartButton = By.cssSelector("#cart button");
    private final By cartTotal = By.cssSelector("#cart-total");
    private final By navbar = By.cssSelector("#menu");
    private final By heroSwiper = By.cssSelector("#slideshow0");
    private final By heroSwiperImages = By.cssSelector("#slideshow0 .swiper-slide img");
    private final By productCards = By.cssSelector(".product-layout");
    private final By brandsSwiper = By.cssSelector("#carousel0");
    private final By brandsSwiperImages = By.cssSelector("#carousel0 .swiper-slide img");
    private final By footer = By.cssSelector("footer");
    private final By successAlert = By.cssSelector(".alert.alert-success");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage() {
        driver.get(homeUrl);
        waitForPageReady();
    }

    public boolean isLogoVisible() {
        return isDisplayed(logo);
    }

    public boolean isSearchInputVisible() {
        return isDisplayed(searchInput);
    }

    public boolean isSearchButtonVisible() {
        return isDisplayed(searchButton);
    }

    public boolean isCartButtonVisible() {
        return isDisplayed(cartButton);
    }

    public boolean isCartTotalVisible() {
        return isDisplayed(cartTotal);
    }

    public boolean isNavbarVisible() {
        return isDisplayed(navbar);
    }

    public boolean isHeroSwiperVisible() {
        return isDisplayed(heroSwiper) && hasElements(heroSwiperImages);
    }

    public boolean areProductCardsVisible() {
        return hasElements(productCards);
    }

    public boolean isBrandsSwiperVisible() {
        return isDisplayed(brandsSwiper) && hasElements(brandsSwiperImages);
    }

    public boolean isFooterVisible() {
        return isDisplayed(footer);
    }

    public int getCartItemCount() {
        String cartText = textOf(cartTotal);
        Pattern pattern = Pattern.compile("(\\d+)\\s+item");
        Matcher matcher = pattern.matcher(cartText);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 0;
    }

    public void searchProduct(String productName) {
        clearAndType(searchInput, productName);
        click(searchButton);
        waitForUrlContains("route=product/search");
        waitForPageReady();
    }

    public void addFeaturedProductToCart(String productName) {
        By productCard = By.xpath(
                "//div[contains(@class,'product-layout')][.//h4/a[normalize-space()='" + productName + "']]"
        );

        WebElement card = waitForVisibility(productCard);
        WebElement addToCartButton = card.findElement(By.cssSelector(".button-group button:first-child"));

        addToCartButton.click();
    }

    public void waitUntilCartItemCountIs(int expectedCount) {
        waitUntil(webDriver -> getCartItemCount() == expectedCount);
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successAlert);
    }

    public void waitUntilCartHasAtLeastOneItem() {
        waitUntil(webDriver -> getCartItemCount() >= 1);
    }

    public void waitUntilSuccessMessageIsDisplayed() {
        waitForVisibility(successAlert);
    }

    public boolean successMessageContainsProduct(String productName) {
        return textOf(successAlert).contains(productName);
    }
}
