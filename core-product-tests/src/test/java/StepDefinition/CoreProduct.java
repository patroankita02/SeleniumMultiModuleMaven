package StepDefinition;

import PageHelper.GoldenStateHomePage;
import PageHelper.MensGoldenStatePage;
import PageHelper.NewsAndMediaPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CoreProduct
{
    private Base base = new Base();
    private GoldenStateHomePage goldenStateHelper;
    private MensGoldenStatePage mensGoldenStateHelper;
    private NewsAndMediaPage newsAndMediaHelper;


    @Given("User lands on CP home page")
    public void user_lands_on_cp_home_page()
    {
        goldenStateHelper = base.getPage(GoldenStateHomePage.class);
        goldenStateHelper.navigateToHomePage();
    }
    @Given("User mousehover on shop icon")
    public void user_mousehover_on_shop_icon()
    {
          goldenStateHelper.hoversMouseOnShopIcon();
    }
    @Given("User clicks on Mens Option")
    public void user_clicks_on_mens_option()
    {
        goldenStateHelper.clicksOnMenOption();
    }
    @Then("Verify that user navigates to a new tab")
    public void verify_that_user_navigates_to_a_new_tab()
    {
         goldenStateHelper.navigatesToTheNewTab();
    }
    @When("User selects jacket from all department section")
    public void user_selects_jacket_from_all_department_section()
    {
        mensGoldenStateHelper=base.getPage(MensGoldenStatePage.class);
        mensGoldenStateHelper.waitForWarriorHomePageLoading();
        mensGoldenStateHelper.selectsJacketFromTheDepartmentFilter();
    }
    @When("Store each Jacket Price,Title and Top Seller message message into a text file")
    public void store_each_jacket_price_title_and_top_seller_message_message_into_a_text_file() throws InterruptedException {
        // get the detail from each page, so check for pagination
        mensGoldenStateHelper.storeEachJacketPriceTitleAndTopSellerMessageToATextFile();
    }
    @When("Attach the text file to the report")
    public void attach_the_text_file_to_the_report() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @And("User mouse hovers menu item")
    public void userMouseHoversMenuItem()
    {
        goldenStateHelper.hoverMouseOnMenu();
    }

    @And("User clicks on News and Features")
    public void userClicksOnNewsAndFeatures()
    {
        goldenStateHelper.clicksOnNewsAndFeatures();

    }

    @Then("User verifies that the videos are displayed")
    public void userVerifiesThatTheVideosAreDisplayed()
    {
        newsAndMediaHelper=base.getPage(NewsAndMediaPage.class);
        newsAndMediaHelper.newsAndVideosDisplayed();
    }

    @And("Count the total no of video feeds")
    public void countTheTotalNoOfVideoFeeds()
    {
        newsAndMediaHelper.countVideoFeeds();
    }


    @And("Count video present from {int} days")
    public void countVideoPresentFromDays(int days)
    {
        newsAndMediaHelper.countVideoFeedsFromGivenNoOfDays(days);
    }
}
