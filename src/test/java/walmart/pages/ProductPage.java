package walmart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import walmart.base.BasePage;

public class ProductPage extends BasePage {

    private final By productTitle = By.cssSelector("#content h1");
    private final By addToCartButton = By.id("button-cart");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductPageDisplayed() {
        waitForPageReady();
        return isDisplayed(productTitle);
    }

    public void addToCart() {
        waitForPageReady();
        click(addToCartButton);
    }

    public String getProductTitle() {
        return textOf(productTitle);
    }
}
