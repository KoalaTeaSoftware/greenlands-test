package testFramework.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import testFramework.Context;
import testFramework.helpers.Urls;
import testSuite.objects.GreenlandsCommonPage;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Steps that all HTML pages can support
 */
public class WebPageSteps {
    // This instance of the use of this set of steps is going to be working within the greenlands context, and some
    // pages may be very slow, so adjust this type to make it specific for this test suite
    private GreenlandsCommonPage myPage;

    private GreenlandsCommonPage getMyPage() {
        if (myPage == null)
            myPage = new GreenlandsCommonPage(Context.defaultDriver);
        return myPage;
    }

    @Given("I navigate to the page {string}")
    public void iNavigateToThePage(String fullUrl) {
        try {
            String s = Urls.interpretURL(fullUrl);
            Context.defaultActor.getResource(s);
        } catch (MalformedURLException | NoSuchFieldException e) {
            Assert.fail("Failed to understand URL." + e.getMessage());
        }
    }

    @Then("the page title is {string}")
    public void thePageTitleIs(String expectedTitle) {
        if (expectedTitle.isEmpty()) {
            try {
                expectedTitle = Context.sutConfiguration.getProperty("defaultTitle");
            } catch (NoSuchFieldException e) {
                Assert.fail("No expected header provided, and no default available either: " + e.getMessage());
            }
        }
        // the page title test is often requested directly after a navigation click.
        getMyPage().waitForTitleToBe(expectedTitle);
    }

    @And("the first heading is {string}")
    public void theFirstHeadingIs(String expected) {
        // This test is often requested directly after a navigation click.
        getMyPage().waitForFirstHeaderToBe(expected);
    }

    @And("the first heading does not contain {string}")
    public void theFirstHeadingDoesNotContain(String needle) {
        boolean haystackDoesContainNeedle = getMyPage().readFirstHeader().contains(needle);

        Assert.assertFalse(
                "The first header :" + myPage.readFirstHeader() + ": should not contain :" + needle + ":",
                haystackDoesContainNeedle);
    }

    @And("the page scheme is {string}")
    public void thePageSchemeIs(String expectedScheme) {
        try {
            if (expectedScheme.isEmpty()) expectedScheme = Context.sutConfiguration.getProperty("defaultScheme");

            Assert.assertEquals("Unexpected scheme for the current page",
                    expectedScheme.replaceAll("[:/]", ""),
                    getMyPage().readLocation().getProtocol()
            );
        } catch (NoSuchFieldException e) {
            Assert.fail("Please define a default scheme, in the SUT configuration file");
        } catch (MalformedURLException e) {
            Assert.fail("Current location can't be determined:" + e.getMessage() + ": ");
        }
    }

    @Then("all images are well drawn")
    public void allImagesAreWellDrawn() {
        List<WebElement> imageList = getMyPage().myDriver.findElements(By.tagName("img"));
        SoftAssert sa = new SoftAssert();
        int idx = 0;

        for (WebElement el : imageList) {
            sa.assertTrue(
                    myPage.browserShowsImage(el),
                    "Image number " + idx + " seems not to have been drawn properly"
            );
            idx++;
        }
        sa.assertAll();
    }
}
