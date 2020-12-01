package testSuite.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import testFramework.Context;
import testSuite.objects.GreenlandsCommonPage;
import testSuite.objects.SecondaryNavWidget;

public class SecondaryNav {
    // Firstly, wait the the page to be drawn
    final GreenlandsCommonPage myPage = new GreenlandsCommonPage();

    // Now, make a purpose-built item for running the tests on
    final SecondaryNavWidget me = new SecondaryNavWidget(Context.defaultDriver);

    @Then("the secondary nav links are present")
    public void theSecondaryNavLinksArePresent() {

        Assert.assertTrue("The contact link should be visible", me.contactLink.isEnabled());
        Assert.assertTrue("The membership link should be visible", me.membershipLink.isEnabled());
        Assert.assertTrue("The policies link should be visible", me.policyLink.isEnabled());
    }

    @When("I follow the secondary nav with text {string}")
    public void iFollowTheSecondaryNavWithText(String linkName) {
        try {
            // although the link is likely to be off the bottom of the page, bth FFx and Chrome will let you click on it
            me.followLink(linkName);
            // This may result in a new page being show, this may take a while.
            // So invoke the 'wait for a Greenlands' page that we know and love
            GreenlandsCommonPage throwAwayPage = new GreenlandsCommonPage();
        } catch (NoSuchElementException e) {
            Assert.fail("Unable to find secondary nave item named :" + linkName + ": " + e.getMessage());
        }
    }

}
