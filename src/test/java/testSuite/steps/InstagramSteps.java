package testSuite.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import testFramework.Context;
import testFramework.objects.BrowserObject;
import testSuite.objects.InstagramPage;
import testSuite.objects.InstagramWidget;

public class InstagramSteps {

    @Then("there are {int} instagram images")
    public void thereAreInstagramImages(int expectedImgCount) {
        InstagramWidget instagramWidget = new InstagramWidget(Context.defaultDriver);

        Assert.assertEquals("Unexpected number of Instagram images",
                expectedImgCount,
                instagramWidget.getImageList().size());
    }

    @And("the instagram images are fully drawn")
    public void theInstagramImagesAreFullyDrawn() {
        InstagramWidget instagramWidget = new InstagramWidget(Context.defaultDriver);
        int index = 0;
        SoftAssert sa = new SoftAssert();
        for (WebElement img : instagramWidget.getImageList()) {
            sa.assertTrue(instagramWidget.browserShowsImage(img), "Image  " + index + " appears faulty");
            index++;
        }
        sa.assertAll();
    }

    @Then("all the individual instagram image links get a greenlands instagram page")
    public void allTheIndividualInstagramImageLinksGetAGreenlandsInstagramPage() {
        final String m = "For img index number %d, the title of the new tab \"%s\" should contain \"the greenlands\" " +
                "and \"instagram\", but does not";

        InstagramWidget instagramWidget = new InstagramWidget(Context.defaultDriver);
        BrowserObject browserObject = new BrowserObject();
        int index = 0;
        SoftAssert sa = new SoftAssert();
        for (WebElement img : instagramWidget.getImageList()) {
            img.click();

            browserObject.waitForTabCount(2);
            browserObject.switchToTabIndexedBy(1); // note this index starts at zero;
            InstagramPage instagramPage = new InstagramPage();

            if (!instagramPage.readPageTitle().toLowerCase().contains("the greenlands")) {
                sa.fail(String.format(m, index, instagramPage.readPageTitle()));
            }

            browserObject.closeCurrentTab(); // make sure that we only have 1 tab
            browserObject.waitForTabCount(1); // belt and braces
            browserObject.switchToTabIndexedBy(0); // It must be told this, otherwise, it tries to use the closed tab
            index++;
        }
        sa.assertAll();
    }

    @When("I follow the follow me on instagram CTA")
    public void iFollowTheFollowMeOnInstagramCTA() {
        InstagramWidget instagramWidget = new InstagramWidget(Context.defaultDriver);

        instagramWidget.followMe();
    }

    @Then("the new page is a greenlands instagram page")
    public void theNewPageIsAGreenlandsInstagramPage() {
        // In its current use, this step will be invoked after the user has clicked on a link on one of our pages
        // therefore, the new page, while looking like the current page, and Selenium must be told to look at it.
        BrowserObject browserObject = new BrowserObject();
        browserObject.switchToTabIndexedBy(1);

        InstagramPage instagramPage = new InstagramPage();

        if (!instagramPage.readPageTitle().toLowerCase().contains("the greenlands")) {
            String msg = String.format(
                    "The title of the new tab \"%s\" should contain \"the greenlands\" and \"instagram\", but does not",
                    instagramPage.readPageTitle()
            );
            Assert.fail(msg);
        }
    }
}
