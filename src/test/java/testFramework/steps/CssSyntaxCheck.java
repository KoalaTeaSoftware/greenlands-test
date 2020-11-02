package testFramework.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import testFramework.helpers.Urls;
import testFramework.objects.W3cCssChecker;

import java.net.MalformedURLException;
import java.time.Duration;

import static testFramework.helpers.Dates.humanReadableDuration;

public class CssSyntaxCheck {
    private final Duration tout = Duration.ofSeconds(25);
    private W3cCssChecker w3cCssValidator;
    private String url;

    @Given("the w3C CSS tester reviews the file {string}")
    public void theWCCSSTesterReviewsTheFile(String urlOfFile) {
        try {
            this.url = Urls.interpretURL(urlOfFile);
            w3cCssValidator = new W3cCssChecker(url, tout);
        } catch (TimeoutException e) {
            Assert.fail("Failed to find results from:" + url + ": within " + humanReadableDuration(tout));
        } catch (MalformedURLException | NoSuchFieldException e) {
            Assert.fail("Failed to process the URL: " + e.getMessage());
        }

    }

    @Then("the w3c CSS tester reports compliance")
    public void theWCCSSTesterReportsCompliance() {
        if (!w3cCssValidator.fileValidates()) {
            testFramework.helpers.Reports.writeToHtmlReport(w3cCssValidator.readResults());
            Assert.fail("File :" + url + ": contains syntax errors.");
        }
    }
}
