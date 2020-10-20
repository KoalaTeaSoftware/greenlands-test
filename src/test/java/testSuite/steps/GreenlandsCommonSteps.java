package testSuite.steps;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import testFramework.Context;
import testSuite.objects.GreenlandsCommonPage;
import testSuite.objects.HomePageObject;

public class GreenlandsCommonSteps {
    private GreenlandsCommonPage greenlandsCommonPage;

    @Then("the secondary nav links are present")
    public void theSecondaryNavLinksArePresent() {
        getMyPage().goToBottomOfPage();

        Assert.assertTrue("The contact link should be visible", getMyPage().contactLink.isDisplayed());
        Assert.assertTrue("The membership link should be visible", getMyPage().membershipLink.isDisplayed());
        Assert.assertTrue("The policies link should be visible", getMyPage().policyLink.isDisplayed());
    }

    private GreenlandsCommonPage getMyPage() {
        if (greenlandsCommonPage == null)
            greenlandsCommonPage = new HomePageObject(Context.defaultDriver);
        return greenlandsCommonPage;
    }

}
