package framework.frameSteps;

import framework.ActorFactory;
import framework.ContextOfScenario;
import framework.ContextOfTest;
import framework.actors.Actor;
import framework.objects.HtmlPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import static org.junit.Assert.fail;

/**
 * This package will be used in things like your background steps to get you to the page that you want
 */
public class FrameworkSteps {
//    @When("I see the default resource")
//    public void iSeeTheDefaultResource() {
//        switch (ContextOfTest.defaultActorType) {
//            case FIREFOX:
//            case CHROME:
//            case INTERNET_EXPLORER:
//            case SAFARI:
//                ContextOfTest.actor.getResource(ContextOfTest.defaultBrowserLocation);
//                break;
//            case ANDROID:
//            case IOS:
//                break;
//            case API:
//                try {
//                    String apiURL = ContextOfTest.defaultBrowserLocation;
//                    apiURL += '/' + ContextOfTest.sutConfiguration.getProperty("defaultApiResource");
//                    ContextOfTest.actor.getResource(apiURL);
//                    break;
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                    fail("Problem requesting the default API endpoint");
//                }
//                break;
//        }
//    }
//
//    @Then("that resource is retrieved")
//    public void thatResourceIsRetrieved() {
//        switch (ContextOfTest.defaultActorType) {
//            case FIREFOX:
//            case CHROME:
//            case INTERNET_EXPLORER:
//            case SAFARI:
//                HomePage homePage = new HomePage(ContextOfScenario.driver);
//
//                String pageTitle = homePage.getPageTitle();
//                ContextOfTest.actor.writeToHtmlReport(String.format("Page has title :%s:\n", pageTitle));
//
//                Assert.assertTrue("The page should have a title", pageTitle.length() > 0);
//                break;
//            case ANDROID:
//            case IOS:
//                break;
//            case API:
//                RandomPostcodeResponse randomPostcodeResponse = new RandomPostcodeResponse(ContextOfTest.actor.getResponse());
//
//                int code = randomPostcodeResponse.getStatus();
//                ContextOfTest.actor.writeToHtmlReport(String.format("The Response code is :%d:", code));
//                ContextOfTest.actor.writeToHtmlReport(String.format("The random Postcode is :%s:", randomPostcodeResponse.getPostcode()));
//
//                Assert.assertEquals("The response code should be OK", code, HttpStatus.SC_OK);
//                break;
//        }
//
//    }

    @When("I use browser {string} to view the page at {string}")
    public void iUseBrowserToViewThePageAt(String requesterBrowser, String requestedURL) {
        Actor.ActorType browserType = null;
        if (requesterBrowser.length() == 0) {
            if (ContextOfTest.defaultBrowserType == null)
                fail("If no browser is defined for this step and no default has be defined in the Test Framework Configuration");
            else
                browserType = ContextOfTest.defaultBrowserType;
        } else {
            try {
                browserType = Actor.ActorType.valueOf(requesterBrowser);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                fail("Unknown type of browser (" + requesterBrowser + ") has been requested");
            }
        }
        if (requestedURL.length() == 0) {
            if (ContextOfTest.defaultBrowserLocation.length() == 0) {
                fail("If no URL is defined for this step and no default has be defined in the Test Framework Configuration");
            } else {
                requestedURL = ContextOfTest.defaultBrowserLocation;
            }
        }

        // overwrite any default actor
        ContextOfScenario.actor = ActorFactory.getActor(browserType);
        ContextOfScenario.actor.getResource(requestedURL);
    }

    @Then("the browser page title contains {string}")
    public void theBrowserPageTitleContains(String requiredTitleContents) {
        if (requiredTitleContents.length() == 0) {
            if (ContextOfTest.defaultUiPageTitleString.length() > 0)
                requiredTitleContents = ContextOfTest.defaultUiPageTitleString;
            else {
                fail("No default page title contents have been defined");
            }
        }

        HtmlPage thePage = new HtmlPage(ContextOfScenario.actor.getDriver());

        Assert.assertTrue(
                "The page title (" + thePage.getPageTitle() + ") should contain (" + requiredTitleContents + ")",
                thePage.getPageTitle().contains(requiredTitleContents)
        );
    }
}


