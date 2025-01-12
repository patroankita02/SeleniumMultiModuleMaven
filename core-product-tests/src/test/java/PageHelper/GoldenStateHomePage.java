package PageHelper;
import StepDefinition.Base;
import Utils.GenericUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoldenStateHomePage extends Base
{
    private GenericUtil genericUtil;
    @FindBy(xpath = "//div[text()='x']")
    private WebElement ticketPopupClose;

    @FindBy(xpath = "//span[text()='Shop']")
    private WebElement shopButton;

    @FindBy(xpath = "//ul[@id='nav-dropdown-desktop-1059653']//a[contains(@title,'Men')]")
    private WebElement menOption;

    @FindBy(xpath = "//li[@class='menu-item']//span[text()='...']")
    private WebElement menuThreeDot;

    @FindBy(xpath = "//li[contains(@class,'menu-item')]//a[@title='News & Features']")
    private WebElement newsAndFeature;



    public GoldenStateHomePage()
    {
       genericUtil = new GenericUtil(getDriver());
    }

    public void navigateToHomePage() {
        genericUtil.navigateToUrl(getYamlDataReader().getData("URL").toString());
        genericUtil.waitForElementToBeVisible(ticketPopupClose,20);
        ticketPopupClose.click();
        genericUtil.waitForElementToBeVisible(shopButton,20);
    }

    public void hoversMouseOnShopIcon()
    {
        genericUtil.hoverOverElement(shopButton);
    }

    public void clicksOnMenOption()
    {
        menOption.click();
    }

    public void navigatesToTheNewTab()
    {
        genericUtil.switchToNewTab();

    }

    public void hoverMouseOnMenu()
    {
        genericUtil.waitForPageLoad();
        genericUtil.waitForElementToBeVisible(menuThreeDot,20);
        genericUtil.hoverOverElement(menuThreeDot);
    }

    public void clicksOnNewsAndFeatures()
    {
        genericUtil.waitForPageLoad();
        genericUtil.waitForElementToBeVisible(newsAndFeature,30);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", newsAndFeature);
        genericUtil.waitForPageLoad();
        jsExecutor.executeScript("arguments[0].click();", newsAndFeature);
        genericUtil.implicitWaitForPage(20);
    }




}
