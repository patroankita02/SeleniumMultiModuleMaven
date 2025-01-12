package PageHelper;
import StepDefinition.Base;
import Utils.GenericUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class NewsAndMediaPage extends Base {
    private GenericUtil genericUtil;

    @FindBy(xpath = "//h3[text()='NEWS']")
    private WebElement newsHeader;

    @FindBy(xpath = "//h3[text()='VIDEOS']")
    private WebElement videoHeader;

    @FindBy(xpath = "//h3[text()='VIDEOS']//parent::div//parent::div//time//span")
    private List<WebElement> videoLinks;

    @FindBy(xpath = "//h3[text()='Connect']")
    private WebElement connectText;

    public NewsAndMediaPage()
    {
        genericUtil = new GenericUtil(getDriver());
    }

    public void newsAndVideosDisplayed()
    {
        genericUtil.waitForPageLoad();
        genericUtil.waitForElementToBeVisible(newsHeader,10);
        genericUtil.waitForElementToBeVisible(videoHeader,5);
        genericUtil.waitForElementToBeVisible(connectText,5);
    }

    public void countVideoFeeds()
    {
        if (videoLinks.size()==0) {
            genericUtil.waitForElementToBeVisible(videoLinks.get(0), 20);
        }
        System.out.println("Total video feeds are : "+ videoLinks.size());

    }

    public void countVideoFeedsFromGivenNoOfDays(int days) {
        int count=0;
        for (WebElement videoLink : videoLinks)
        {
            if(count==0)
            {
                count=1;
            }else
            {
                String duration = videoLink.getText().trim();
                if (duration.contains("d") && Character.getNumericValue(duration.charAt(0)) <= days)
                    count += 1;
            }
        }
        System.out.println(days+" Days video links are :"+ count);
    }



}
