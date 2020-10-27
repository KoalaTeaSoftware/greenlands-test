package testFramework.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import testFramework.Context;
import testFramework.helpers.Urls;
import testFramework.objects.WebPageObj;

import java.net.MalformedURLException;

/**
 * Steps that all HTML pages can support
 */
public class WebPageSteps {
    private WebPageObj myPage;

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

    private WebPageObj getMyPage() {
        if (myPage == null)
            myPage = new WebPageObj(Context.defaultDriver);
        return myPage;
    }
}
