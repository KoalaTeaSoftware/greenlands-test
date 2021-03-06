package testSuite.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import testSuite.objects.SecondaryNavWidget;

public class SecondaryNav {
    // Firstly, wait the the page to be drawn
    //    final GreenlandsCommonPage myPage = new GreenlandsCommonPage();

    // Now, make a purpose-built item for running the tests on
    final SecondaryNavWidget me = new SecondaryNavWidget();

    @Then("the secondary nav links are present")
    public void theSecondaryNavLinksArePresent() {

        Assert.assertTrue("The contact link should be visible", me.contactLink.isEnabled());
//        Assert.assertTrue("The membership link should be visible", me.membershipLink.isEnabled());
        Assert.assertTrue("The policies link should be visible", me.policyLink.isEnabled());
    }

    @When("I follow the secondary nav with text {string}")
    public void iFollowTheSecondaryNavWithText(String linkName) {
        try {
            // although the link is likely to be off the bottom of the page, bth FFx and Chrome will let you click on it
            me.followLink(linkName);
        } catch (NoSuchElementException e) {
            Assert.fail("Unable to find secondary nave item named :" + linkName + ": " + e.getMessage());
        }
    }

}
