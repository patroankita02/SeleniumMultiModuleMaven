package PageHelper;

import StepDefinition.Base;
import Utils.GenericUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class MensGoldenStatePage extends Base {

    private GenericUtil genericUtil;

    @FindBy(xpath = "//a[text()='Golden State Warriors']")
    private WebElement goldenStateWarriorText;

    @FindBy(xpath = "//a//span[text()='Jackets']")
    private WebElement shopJacket;

    @FindBy(xpath = "//span[text()='Golden State Warriors Men Jackets']")
    private WebElement goldenStateWarriorMenJacketText;

    @FindBy(xpath = "//div[@class='product-grid-top-area']//a[@aria-label='next page']")
    private WebElement nextPageButton;

    @FindBy(xpath = "//span[@class='money-value']//span[contains(text(),'$')]")
    private List<WebElement> jacketPricesText;;

    @FindBy(xpath = "//div[@class='product-card-title']//a")
    private List<WebElement> jacketTitle;

    @FindBy(xpath = "//i[@aria-label='Close Pop-Up']")
    private WebElement closeShippingPopUp;

    public MensGoldenStatePage() {
        genericUtil = new GenericUtil(getDriver());

    }

    public void waitForWarriorHomePageLoading()
    {
        genericUtil.implicitWaitForPage(10);
       try {
           if (closeShippingPopUp.isDisplayed())
               closeShippingPopUp.click();
       }catch (Exception e) {
           genericUtil.waitForElementToBeVisible(goldenStateWarriorText, 40);
       }
    }

    public void selectsJacketFromTheDepartmentFilter()
    {
        shopJacket.click();
        genericUtil.waitForElementToBeVisible(goldenStateWarriorMenJacketText,20);
    }

    public void storeEachJacketPriceTitleAndTopSellerMessageToATextFile() throws InterruptedException {
        int count = 1;
        List<String> totaljackets = new ArrayList<>();
        while (true) {
            // Dynamically fetch the list of jackets on the current page
            List<WebElement> jacketTitleList = jacketTitle;
            List<WebElement> jacketPriceList = jacketPricesText;

            // Iterate over the jackets on the current page
            for (int i = 0; i < jacketTitleList.size(); i++)
            {
                Thread.sleep(1000); // Avoid rapid interactions
                String jacketTitle = jacketTitleList.get(i).getText();
                String jacketPrice = jacketPriceList.get(i).getText();
                totaljackets.add("Title" + count + " : " + jacketTitle + " , Price" + count + " : " + jacketPrice);
                count++;
            }

            // Re-locate the "Next Page" button and check if it is disabled
            String isDisabled = genericUtil.getAttribute(nextPageButton,"aria-disabled");

            // Break the loop if the next page button is disabled
            if ("true".equalsIgnoreCase(isDisabled)) {
                break;
            }

            // Click the next page button
            nextPageButton.click();

            // Wait for the next page to load
            genericUtil.waitForPageLoad();
            //genericUtil.implicitWaitForPage(20);
        }
            //Store the details under a .txt file
            genericUtil.saveToTextFile(totaljackets,"JacketDetails.txt");

//            String fileName = "src/test/resources/jacketDetails.txt";
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
//            {
//                for (String detail : totaljackets) {
//                    writer.write(detail);
//                    writer.newLine();
//                }
//            } catch (IOException e) {
//                System.err.println("An error occurred while writing to the file: " + e.getMessage());
//            }
    }



}