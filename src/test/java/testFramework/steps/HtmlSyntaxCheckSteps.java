package testFramework.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import testFramework.helpers.Reports;
import testFramework.helpers.resourceLocator;
import testFramework.objects.W3cHtmlChecker;

import java.time.Duration;

import static testFramework.helpers.DateHelpers.humanReadableDuration;

public class HtmlSyntaxCheckSteps {
    final Duration tout = Duration.ofSeconds(40);
    private W3cHtmlChecker w3cHtmlValidator = null;
    private String url;

    @Given("the w3C HTML tester reviews the file {string}")
    public void theW3CHTMLTesterReviewsTheFile(String urlOfFile) {
        url = resourceLocator.interpretURL(urlOfFile);
        try {
            w3cHtmlValidator = new W3cHtmlChecker(url, tout);
        } catch (TimeoutException e) {
            Assert.fail("Failed to find results from:" + url + ": in " + humanReadableDuration(tout) + " " +
                    "seconds");
        }

    }

    @Then("the w3c HTML tester reports compliance")
    public void theW3CHTMLTesterReportsCompliance() {
        if (!w3cHtmlValidator.fileValidates()) {
            Reports.writeToHtmlReport(w3cHtmlValidator.getErrorList());
            Assert.fail(url + ": (unescaped) should be syntactically correct");
        }
    }
}
