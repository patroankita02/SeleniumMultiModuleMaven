package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GenericUtil
{
    WebDriver driver;
    Actions actions;
    WebDriverWait wait;

    public GenericUtil(WebDriver driver)
    {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    //Page navigation
    public void navigateToUrl(String URL)
    {
        driver.get(URL);
    }

    //Find Element
    public WebElement findElement(By locator)
    {
        return driver.findElement(locator);
    }

    //Click element
    public void clickElement(By locator) {
        WebElement element = findElement(locator);
        element.click();
    }

    //Wait for page load using javascriptExecutor
    public void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver)
            {
                return ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete");
            }
        });
    }
    //Wait for Element to be visisble
    public WebElement waitForElementToBeVisible(WebElement element, int timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    //Wait for Element to be located
    public WebElement waitForElementToBeLocated(By locator, int timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    //Wait DOM
    public void implicitWaitForPage(int timeInSeconds)
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
    }

    //Get Attribute
    public String getAttribute(WebElement element, String attributeName)
    {
        return element.getAttribute(attributeName);
    }

    //MouseHover
    public void hoverOverElement(WebElement element)
    {
        actions.moveToElement(element).perform();
    }

    //Window Switch
    public void switchToNewTab() {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1)); // Switch to the last opened tab
    }

    //Save Data To File
    public void saveToTextFile(List<String> details, String filePath)
    {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
            {
                for (String detail : details) {
                    writer.write(detail);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
            }
    }

    public void scrollDown(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void javaScriptClick(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }




}
