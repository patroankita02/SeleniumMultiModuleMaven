package StepDefinition;

import Utils.Listener;
import Utils.YAMLReader;
import factory.DriverFactory;
import factory.PageFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Base
{
    @Before(order = 0)
    public void launchBrowserForCoreProduct()
    {
//        browser = "chrome";
//        // Initialize WebDriver with the browser TestNG parameter
//        String finalBrowser = (browser != null) ? browser : browser;
//        driver = DriverFactory.init_driver(finalBrowser);
        DriverFactory.init_driver(Listener.getBrowser(), Listener.getYamlPath());
    }

    @After(order = 0)
    public void tearDownDriver(Scenario scenario)
    {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot On Failure");
        }

        // Attach jacket detail file.
        try {
            Path filePath = Paths.get("target/jacketDetails.txt");
            if (Files.exists(filePath)) {
                String fileContent = Files.readString(filePath);
                scenario.attach(fileContent, "text/plain", "Jacket Details");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @After(order = 1)
    public void quitBrowser()
    {
        DriverFactory.quitDriver();
    }

    public WebDriver getDriver()
    {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            throw new RuntimeException("WebDriver not initialized. Please call respective browser driver setUp()");
        }
        return driver;
    }

    // Provide access to YamlDataReader for use in helper classes
    public YAMLReader getYamlDataReader()
    {
        return DriverFactory.getYamlReader();
    }

    public <T> T getPage(Class<T> pageClass)
    {
        return PageFactory.get_page(getDriver(), pageClass);
    }
}
