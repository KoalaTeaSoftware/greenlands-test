package testSuite.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;
import testFramework.Context;
import testSuite.objects.HomePageObject;

public class HomePageSteps {


    private HomePageObject homePageObject;

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
                    getMyPage().browserShowsImage(getMyPage().getImgForChapter(thisChapter)),
                    "Image for chapter " + thisChapter + " appears faulty.");
        }
        sa.assertAll();
    }

    @When("I go to the chapter called {string}")
    public void iGoToTheChapterCalled(String chapterName) {
        getMyPage().getLinkForChapter(chapterName).click();
    }

    private HomePageObject getMyPage() {
        if (homePageObject == null)
            homePageObject = new HomePageObject(Context.defaultDriver);
        return homePageObject;
    }

}
