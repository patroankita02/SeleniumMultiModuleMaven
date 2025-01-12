package factory;

import Utils.YAMLReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Map;


public class DriverFactory
{
    public WebDriver driver;
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static ThreadLocal<YAMLReader> tlYamlReader = new ThreadLocal<>();

    //Initilaize WebDriver
    public static void init_driver(String browser, String yamlFilePath)
    {
        System.out.println("Browser value is: "+browser);
        if (browser.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            tlDriver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("firefox"))
        {
            WebDriverManager.firefoxdriver().setup();
            tlDriver.set(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase("safari"))
        {
            tlDriver.set(new SafariDriver());
        } else
        {
            throw new RuntimeException("Unsupported browser: " + browser);
        }
        getDriver().manage().window().maximize();

        if (tlYamlReader.get() == null) {
            YAMLReader yamlDataReader = new YAMLReader(yamlFilePath);
            tlYamlReader.set(yamlDataReader);
            System.out.println("YAML data loaded from: " + yamlFilePath);

            // Debug print for loaded YAML data
            System.out.println("Debug: Loaded YAML data contents:");
            for (Map.Entry<String, Object> entry : yamlDataReader.getAllData().entrySet()) {
                System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            }
        }

    }

    //Get WebDriver and made it thread safe for parallel test
    public static WebDriver getDriver()
    {
        if (tlDriver.get() == null) {
            throw new RuntimeException("Initilaise the driver first.");
        }
       return tlDriver.get();
    }
    public static void quitDriver()
    {
        if (tlDriver.get() != null)
        {
            tlDriver.get().quit();
            tlDriver.remove();
        }
        if (tlYamlReader.get() != null)
        {
            tlYamlReader.remove();
        }
    }
    public static YAMLReader getYamlReader()
    {
        if (tlYamlReader.get() == null) {
            throw new RuntimeException("Initilaise the Data Reader");
        }
        return tlYamlReader.get();
    }

}
