package testSteps;

import framework.ContextOfScenario;
import framework.helpers.HtmlReport;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import objects.GreenlandsCommonPage;
import objects.HomePageObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

public class HomePageSteps {
    @Then("the secondary nav links are present")
    public void theSecondaryNavLinksArePresent() {
        GreenlandsCommonPage homePage = new GreenlandsCommonPage(ContextOfScenario.actor.getDriver());

        homePage.goToBottomOfPage();

        Assert.assertTrue("The contact link should be visible", homePage.contactLink.isDisplayed());
        Assert.assertTrue("The membership link should be visible", homePage.membershipLink.isDisplayed());
        Assert.assertTrue("The policies link should be visible", homePage.policyLink.isDisplayed());
    }

    @And("the chapter images are present")
    public void theChapterImagesArePresent() {
        HomePageObject homePageObject = new HomePageObject(ContextOfScenario.actor.getDriver());

        String[] chapters = {
                "Bestiary",
                "Roots",
                "Art",
                "Music",
                "Stories",
                "Herbs"
        };

        boolean stepFails = false;

        for (String thisChapter : chapters) {
            WebElement thisImgElement = homePageObject.getImgForChapter(thisChapter);
            if (!thisImgElement.isDisplayed()) {
                // it is very likely that the Selenium isDisplayed is good enough for this because it is not related to the viewport
                stepFails = true;
                HtmlReport.writeScreenShotToHtmlReport("The image for chapter " + thisChapter + " should be present");
            }
            if (thisImgElement.getAttribute("src").length() < 1) {
                stepFails = true;
                HtmlReport.writeScreenShotToHtmlReport("The image for chapter " + thisChapter + " should have a src attribute of adequate length");
            }
            if (Integer.valueOf(thisImgElement.getAttribute("width")) < 10) {
                stepFails = true;
                HtmlReport.writeScreenShotToHtmlReport("The image for chapter " + thisChapter + " should have a width attribute of more than 10");
            }
        }
        Assert.assertFalse(stepFails);
    }
}
