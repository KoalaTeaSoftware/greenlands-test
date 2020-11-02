package testFramework.steps;

import io.cucumber.java.en.Then;
import testFramework.Context;
import testSuite.objects.GreenlandsCommonPage;

public class musicPage {
    @Then("all music images are well drawn")
    public void allMusicImagesAreWellDrawn() {
        GreenlandsCommonPage myPage = new GreenlandsCommonPage(Context.defaultDriver);

        //        myPage.all
    }
}
