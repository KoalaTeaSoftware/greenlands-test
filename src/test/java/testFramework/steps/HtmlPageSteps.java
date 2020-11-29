package testFramework.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import testFramework.Context;
import testFramework.helpers.resourceLocator;
import testFramework.objects.HtmlPageObject;

import java.util.List;

/**
 * Test steps that all HTML pages can support
 * There really should be used directly in the test suite, create your own page object
 */
public class HtmlPageSteps {
    private HtmlPageObject page;

    private HtmlPageObject getMyPage() {
        if (page == null)
            page = new HtmlPageObject();
        return page;
    }

    @Given("I navigate to the page {string}")
    public void iNavigateToThePage(String fullUrl) {
        String s = resourceLocator.interpretURL(fullUrl);
        Assert.assertNotNull("Unable to make the URL work", s);
        Context.defaultActor.getResource(s);
    }

    @Then("the page title is {string}")
    public void thePageTitleIs(String expected) {
        if (expected.isEmpty()) {
            try {
                expected = Context.sutConfiguration.getProperty("defaultTitle");
                Assert.assertEquals("The page title is not as expected", expected, getMyPage().readPageTitle());
            } catch (NoSuchFieldException e) {
                Assert.fail("The expected title has not been defined in the UST configuration, and not defined in " +
                        "this test step");
            }
        }
        Assert.assertEquals("Title not as expected", expected, getMyPage().readPageTitle());
    }

    @And("the first heading is {string}")
    public void theFirstHeadingIs(String expected) {
        Assert.assertEquals("Unexpected H1", expected, getMyPage().readFirstHeader());
    }

    @And("the first heading does not contain {string}")
    public void theFirstHeadingDoesNotContain(String needle) {
        boolean haystackDoesContainNeedle = getMyPage().readFirstHeader().contains(needle);

        Assert.assertFalse(
                "The first header " + page.readFirstHeader() + ":should not contain :" + needle + ":",
                haystackDoesContainNeedle);
    }

    @And("the page scheme is {string}")
    public void thePageSchemeIs(String expectedScheme) {
        if (expectedScheme.isEmpty()) {
            try {
                expectedScheme = Context.sutConfiguration.getProperty("defaultScheme");
            } catch (NoSuchFieldException e) {
                Assert.fail("Please define a default scheme, in the SUT configuration file");
            }
        }
        Assert.assertEquals("Unexpected scheme for the current page",
                expectedScheme.replaceAll("[:/]", ""),
                getMyPage().readCurrentLocation().getProtocol());
    }


    @Then("all images are well formed")
    public void allImagesAreWellFormed() {
        SoftAssert sa = new SoftAssert();
        List<WebElement> imgList = getMyPage().listImgTags();

        final int numImgs = imgList.size();

        for (int i = 0; i < numImgs; i++) {
            sa.assertTrue(
                    getMyPage().browserShowsImage(imgList.get(i)),
                    "Image number " + i + "does not appear to be well formed");
        }
        sa.assertAll();
    }
}
