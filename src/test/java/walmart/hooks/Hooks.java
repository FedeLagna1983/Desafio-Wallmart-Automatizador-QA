package walmart.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import walmart.factory.DriverFactory;

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverFactory.getCurrentDriver();

            if (driver instanceof TakesScreenshot screenshotCapableDriver) {
                byte[] screenshot = screenshotCapableDriver.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "failure-screenshot");
            }
        }

        DriverFactory.quitDriver();
    }
}
