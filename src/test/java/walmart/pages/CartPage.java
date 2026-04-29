package walmart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import walmart.base.BasePage;

import java.math.BigDecimal;
import java.util.List;

public class CartPage extends BasePage {

    private final By shoppingCartTitle = By.cssSelector("#content h1");
    private final By shoppingCartTopLink = By.cssSelector("a[title='Shopping Cart'][href*='route=checkout/cart']");
    private final By productTable = By.xpath("//div[@id='content']//table[contains(@class,'table-bordered')][.//thead]");
    private final By continueShoppingButton = By.cssSelector("a[href*='route=common/home'].btn.btn-default");
    private final By checkoutButton = By.cssSelector("a[href*='route=checkout/checkout'].btn.btn-primary");
    private final By productTableHeaders = By.xpath("//div[@id='content']//table[contains(@class,'table-bordered')][.//thead]//thead//td");
    private final By cartProductName = By.cssSelector(".table-responsive td.text-left a");
    private final By cartButton = By.cssSelector("#cart button");
    private final By viewCartLink = By.xpath("//strong[contains(text(),'View Cart') or contains(text(),'Shopping Cart')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return textOf(cartProductName);
    }

    public void openCartDropdown() {
        // Kept for backward compatibility with existing step definitions.
    }

    public void clickViewCart() {
    // 🔥 abrir el dropdown primero
    click(cartButton);

    // 🔥 esperar que aparezca el link
    waitForVisibility(viewCartLink);

    // 🔥 click robusto (JS evita overlays)
    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();",
            driver.findElement(viewCartLink)
    );

    // 🔥 esperar navegación real
    waitForUrlContains("route=checkout/cart");
    waitForPageReady();
    }

    public void openCartPageFromTopMenu() {
        click(cartButton);
        waitForVisibility(viewCartLink);
        click(viewCartLink);
        waitForUrlContains("route=checkout/cart");
        waitForPageReady();
    }

    public boolean isShoppingCartPageDisplayed() {
        waitForUrlContains("route=checkout/cart");

        return driver.getCurrentUrl().contains("route=checkout/cart")
                && isDisplayed(shoppingCartTitle);
    }

    public boolean isProductVisibleInCart(String productName) {
        return isDisplayed(getProductRowLocator(productName));
    }

    public boolean isQuantityFieldDisplayedForProduct(String productName) {
        WebElement row = waitForVisibility(getProductRowLocator(productName));
        return row.findElement(By.cssSelector("input[name^='quantity']")).isDisplayed();
    }

    public boolean isQuantityFieldEditableForProduct(String productName) {
        WebElement row = waitForVisibility(getProductRowLocator(productName));
        WebElement quantityInput = row.findElement(By.cssSelector("input[name^='quantity']"));

        return quantityInput.isDisplayed() && quantityInput.isEnabled();
    }

    public boolean isContinueShoppingButtonDisplayed() {
        return isDisplayed(continueShoppingButton);
    }

    public boolean isCheckoutButtonDisplayed() {
        return isDisplayed(checkoutButton);
    }

    public boolean areProductTableColumnsDisplayed() {
        List<WebElement> headers = findElements(productTableHeaders);

        return isDisplayed(productTable)
                && headers.size() == 6
                && headers.get(0).getText().equals("Image")
                && headers.get(1).getText().equals("Product Name")
                && headers.get(2).getText().equals("Model")
                && headers.get(3).getText().equals("Quantity")
                && headers.get(4).getText().equals("Unit Price")
                && headers.get(5).getText().equals("Total");
    }

    public BigDecimal getUnitPriceForProduct(String productName) {
        WebElement row = waitForVisibility(getProductRowLocator(productName));
        String unitPriceText = row.findElement(By.xpath("./td[5]")).getText();

        return convertPriceToNumber(unitPriceText);
    }

    public BigDecimal getRowTotalForProduct(String productName) {
        WebElement row = waitForVisibility(getProductRowLocator(productName));
        String rowTotalText = row.findElement(By.xpath("./td[6]")).getText();

        return convertPriceToNumber(rowTotalText);
    }

    public int getQuantityForProduct(String productName) {
        WebElement row = waitForVisibility(getProductRowLocator(productName));
        String quantity = row.findElement(By.cssSelector("input[name^='quantity']")).getAttribute("value");

        return Integer.parseInt(quantity);
    }

    public void updateQuantityForProduct(String productName, String quantity) {
        WebElement row = waitForVisibility(getProductRowLocator(productName));
        WebElement quantityInput = row.findElement(By.cssSelector("input[name^='quantity']"));

        quantityInput.clear();
        quantityInput.sendKeys(quantity);

        WebElement updateButton = row.findElement(By.cssSelector("button[data-original-title='Update']"));
        updateButton.click();

        waitForPageReady();
        waitForUrlContains("route=checkout/cart");
    }

    public boolean isRowTotalRecalculatedCorrectly(String productName) {
        BigDecimal unitPrice = getUnitPriceForProduct(productName);
        int quantity = getQuantityForProduct(productName);
        BigDecimal expectedTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal actualTotal = getRowTotalForProduct(productName);

        return expectedTotal.compareTo(actualTotal) == 0;
    }

    private By getProductRowLocator(String productName) {
        return By.xpath(
                "//div[@id='content']//table[contains(@class,'table-bordered')][.//thead]//tbody/tr[.//a[normalize-space()='"
                        + productName +
                        "']]"
        );
    }

    private BigDecimal convertPriceToNumber(String priceText) {
        String cleanedPrice = priceText
                .replace("$", "")
                .replace(",", "")
                .trim();

        return new BigDecimal(cleanedPrice);
    }

    public void continueToCheckout() {
        click(checkoutButton);
        waitForUrlContains("route=checkout/checkout");
        waitForPageReady();
    }
}
