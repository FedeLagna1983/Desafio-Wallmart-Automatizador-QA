package walmart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import walmart.base.BasePage;

public class SearchResultsPage extends BasePage {

    private final By searchResultsTitle = By.cssSelector("#content h1");
    private final By productCards = By.cssSelector("#content .product-layout");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSearchResultsPageDisplayed() {
        return driver.getCurrentUrl().contains("route=product/search")
                && isDisplayed(searchResultsTitle);
    }

    public boolean hasResults() {
        return findElements(productCards).size() > 0;
    }

    public boolean isResultsDisplayed() {
    return driver.findElements(By.cssSelector(".product-layout")).size() > 0;
    }

    public void openProduct(String productName) {
        By productLink = By.xpath(
                "//div[@id='content']//div[contains(@class,'product-layout')]//h4/a[normalize-space()='"
                        + productName +
                        "']"
        );

        click(productLink);
        waitForUrlContains("route=product/product");
        waitForPageReady();
    }
}
