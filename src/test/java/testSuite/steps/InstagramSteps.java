package testSuite.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import testFramework.Context;
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

    @Then("all the individual instagram image links get the expected page")
    public void allTheIndividualInstagramImageLinksGetTheExpectedPage() {
        InstagramWidget instagramWidget = new InstagramWidget(Context.defaultDriver);
        int index = 0;
        SoftAssert sa = new SoftAssert();
        for (WebElement img : instagramWidget.getImageList()) {
            img.click();
            String newTitle = instagramWidget.readPageTitle(1).toLowerCase();
            if (!(newTitle.contains("the greenlands") && newTitle.contains("instagram"))) {
                sa.fail(
                        String.format("For img link index %d, the Title of the new tab \"%s\" should contain \"the " +
                                        "greenlands\" and \"instagram\", but does not",
                                index, newTitle
                        )
                );
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

    @Then("the new page title is good")
    public void theNewPageTitleIsGood() {
        InstagramWidget instagramWidget = new InstagramWidget(Context.defaultDriver);

        String newTitle = instagramWidget.readPageTitle(1).toLowerCase();
        if (!(newTitle.contains("the greenlands") && newTitle.contains("instagram"))) {
            Assert.fail(
                    "The Title of the new tab (" +
                            newTitle +
                            ")should contain \"the greenlands\" and \"instagram\", but does not"
            );
        }
    }
}
