package walmart.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isVisible(By locator) {
        return isDisplayed(locator);
    }

    protected void click(By locator) {
        waitForClickability(locator).click();
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void clearAndType(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String textOf(By locator) {
        return waitForVisibility(locator).getText();
    }

    protected String getAttribute(By locator, String attribute) {
        return waitForVisibility(locator).getAttribute(attribute);
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected boolean hasElements(By locator) {
        return !findElements(locator).isEmpty();
    }

    protected void waitUntil(Function<WebDriver, Boolean> condition) {
        wait.until(condition);
    }

    protected void waitForUrlContains(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    protected void waitForPageReady() {
        wait.until(webDriver -> {
            Object readyState = ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState");

            return "complete".equals(readyState);
        });
    }
}
