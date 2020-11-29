package testSuite.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;
import testSuite.objects.GreenlandsCommonPage;
import testSuite.objects.HomePageObject;

public class HomePageSteps {
    // Assume that all of the home page steps will run against a single home page
    private final HomePageObject homePageObject = new HomePageObject();

    @And("the chapter images are present")
    public void theChapterImagesArePresent() {
        SoftAssert sa = new SoftAssert();

        String[] chapters = {
                "Bestiary",
                "Roots",
                "Art",
                "Music",
                "Stories",
                "Herbs"
        };

        for (String thisChapter : chapters) {
            sa.assertTrue(
                    homePageObject.browserShowsImage(homePageObject.getImgForChapter(thisChapter)),
                    "Image for chapter " + thisChapter + " appears faulty.");
        }
        sa.assertAll();
    }

    @When("I go to the chapter called {string}")
    public void iGoToTheChapterCalled(String chapterName) {
        homePageObject.getLinkForChapter(chapterName).click();
        // cause it to wait for the new page
        GreenlandsCommonPage newPage = new GreenlandsCommonPage();
    }

}
