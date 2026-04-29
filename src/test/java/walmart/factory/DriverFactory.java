package walmart.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import walmart.config.TestConfig;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVERS = new ThreadLocal<>();

    public static WebDriver getDriver() {
        WebDriver driver = DRIVERS.get();

        if (driver == null) {
            driver = createDriver();
            DRIVERS.set(driver);
        }

        return driver;
    }

    public static WebDriver getCurrentDriver() {
        return DRIVERS.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVERS.get();

        if (driver != null) {
            driver.quit();
            DRIVERS.remove();
        }
    }

    private static WebDriver createDriver() {
        String browser = TestConfig.getBrowser();
        WebDriver driver;

        switch (browser) {
            case "chrome" -> driver = createChromeDriver();
            case "firefox" -> driver = createFirefoxDriver();
            case "edge" -> driver = createEdgeDriver();
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser + ". Supported: chrome, firefox, edge."
            );
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        if (!TestConfig.isHeadless()) {
            driver.manage().window().maximize();
        }

        return driver;
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-insecure-localhost");

        if (TestConfig.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);

        if (TestConfig.isHeadless()) {
            options.addArguments("-headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();

        EdgeOptions options = new EdgeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-insecure-localhost");

        if (TestConfig.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        return new EdgeDriver(options);
    }
}
