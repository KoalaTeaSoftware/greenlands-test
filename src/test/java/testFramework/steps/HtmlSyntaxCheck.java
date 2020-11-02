package testFramework.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import testFramework.helpers.Urls;
import testFramework.objects.W3cHtmlChecker;

import java.net.MalformedURLException;
import java.time.Duration;

import static testFramework.helpers.Dates.humanReadableDuration;

public class HtmlSyntaxCheck {
    final Duration tout = Duration.ofSeconds(40);
    private W3cHtmlChecker w3cHtmlValidator = null;
    private String url;

    @Given("the w3C HTML tester reviews the file {string}")
    public void theW3CHTMLTesterReviewsTheFile(String urlOfFile) {
        try {
            url = Urls.interpretURL(urlOfFile);
            w3cHtmlValidator = new W3cHtmlChecker(url, tout);
        } catch (TimeoutException e) {
            Assert.fail("Failed to find results from:" + url + ": in " + humanReadableDuration(tout));
        } catch (MalformedURLException | NoSuchFieldException e) {
            Assert.fail("Failed to understand URL." + e.getMessage());
        }
    }

    @Then("the w3c HTML tester reports compliance")
    public void theW3CHTMLTesterReportsCompliance() {
        if (!w3cHtmlValidator.fileValidates()) {
            testFramework.helpers.Reports.writeToHtmlReport(w3cHtmlValidator.readResults());
            Assert.fail();
        }
    }
}
