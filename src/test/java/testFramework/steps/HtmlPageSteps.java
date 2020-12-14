package testFramework.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import testFramework.Context;
import testFramework.helpers.SoftAssert;
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
        try {
            if (expected.isEmpty()) {
                expected = Context.sutConfiguration.getProperty("defaultTitle");
            }
            Assert.assertEquals("Title not as expected", expected, getMyPage().readPageTitle());
        } catch (NoSuchFieldException e) {
            Assert.fail("The expected title has to be defined either in SUT configuration, or in the test step");
        }
    }

    @Then("the page title becomes {string}")
    public void thePageTitleBecomes(String expected) {
        try {
            if (expected.isEmpty()) {
                expected = Context.sutConfiguration.getProperty("defaultTitle");
            }
            getMyPage().waitForPageTitleToBe(expected);
        } catch (NoSuchFieldException e) {
            Assert.fail("The expected title has to be defined either in SUT configuration, or in the test step");
        } catch (TimeoutException e) {
            // want this to be a controlled exit
            Assert.fail(
                    String.format("Page title (%s) never became as expected (%s)", getMyPage().readPageTitle(), expected)
            );
        }
        // but I don't want it to ever get away with it
        Assert.assertEquals(
                String.format("Page title (%s) should be (%s)", getMyPage().readPageTitle(), expected),
                expected, getMyPage().readPageTitle()
        );
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
            // if the class indicates that it is a special affiliate link, then ignore it
            if (!imgList.get(i).getAttribute("class").contains("amazonAffiliateTrackingLink"))
                sa.assertTrue(
                        getMyPage().
                                browserShowsImage(
                                        imgList.get(i)),
                        String.format(
                                "Image number %d, with src of :%s: does not appear to be well formed",
                                i,
                                imgList.get(i).getAttribute("src")
                        )
                );
        }
        sa.assertAll();
    }

}
