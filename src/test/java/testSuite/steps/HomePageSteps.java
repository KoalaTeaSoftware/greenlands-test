package testSuite.steps;

import io.cucumber.java.en.When;
import testSuite.objects.HomePageObject;

public class HomePageSteps {
    // Assume that all of the home page steps will run against a single home page
    private final HomePageObject homePageObject = new HomePageObject();


    @When("I go to the chapter called {string}")
    public void iGoToTheChapterCalled(String chapterName) {
        homePageObject.getLinkForChapter(chapterName).click();
    }

}
