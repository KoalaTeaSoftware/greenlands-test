package testFramework.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import testFramework.helpers.Urls;
import testFramework.objects.W3cLinkChecker;

import java.net.MalformedURLException;
import java.time.Duration;

import static testFramework.helpers.Dates.humanReadableDuration;

public class PageLinkCheck {
    final Duration tout = Duration.ofSeconds(240); // the link checker can be quite slow

    private W3cLinkChecker w3cLinkChecker;
    private String url;

    @Given("the w3c link checker reviews the file {string}")
    public void theW3CLinkCheckerReviewsTheFile(String urlOfFile) {
        try {
            url = Urls.interpretURL(urlOfFile);
            w3cLinkChecker = new W3cLinkChecker(url, tout);
        } catch (TimeoutException e) {
            Assert.fail("Failed to find results from:" + url + ": in " + humanReadableDuration(tout) + " seconds");
        } catch (MalformedURLException | NoSuchFieldException e) {
            Assert.fail("Failed to understand URL." + e.getMessage());
        }
    }

    @Then("the w3c link checker reports compliance")
    public void theW3CLinkCheckerReportsCompliance() {
        Assert.assertTrue("Links on page :" + url + ": appear broken", w3cLinkChecker.fileValidates());
    }
}
