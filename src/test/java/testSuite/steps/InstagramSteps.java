package testSuite.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import testFramework.Context;
import testFramework.helpers.Reports;
import testFramework.helpers.SoftAssert;
import testFramework.objects.BrowserObject;
import testSuite.objects.InstagramPage;
import testSuite.objects.InstagramWidget;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        InstagramWidget instagramWidget = new InstagramWidget(Context.defaultDriver);
        BrowserObject browserObject = new BrowserObject();
        int index = 0;
        SoftAssert sa = new SoftAssert();
        for (WebElement img : instagramWidget.getImageList()) {
            img.click();

            browserObject.waitForTabCount(2);
            browserObject.switchToTabIndexedBy(1); // note this index starts at zero;
            try {
                InstagramPage instagramPage = new InstagramPage();
            } catch (TimeoutException e) {
                Assert.fail("Failed to get a suitable Instagram page for image index number " + index);
                Reports.writeScreenShotToHtmlReport("This is the faulty page");
            }

            browserObject.closeCurrentTab(); // make sure that we only have 1 tab
            browserObject.waitForTabCount(1); // belt and braces
            browserObject.switchToTabIndexedBy(0); // It must be told this, otherwise, it tries to use the closed tab
            // have a go at adding a pause to try to fool the Instagram bot detector
            Random r = new Random();
            try {
                TimeUnit.SECONDS.sleep(r.nextInt(60 - 30) + 30);
            } catch (InterruptedException ignored) {
            }
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

        try {
            instagramPage.waitForPageTitleToContain("The Greenlands");
        } catch (TimeoutException e) {
            String msg = String.format(
                    "The title of the new tab \"%s\" should contain \"The Greenlands\", but does not",
                    instagramPage.readPageTitle()
            );
            if (instagramPage.botWarningPresent())
                Reports.writeToHtmlReport("The 'bot rejection' message was present, so this may not be an error.");
            Reports.writePageSourceToHtmlReport();
            Assert.fail(msg);
        }
    }
}
