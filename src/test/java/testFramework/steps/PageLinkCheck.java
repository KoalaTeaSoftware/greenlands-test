package testFramework.steps;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import testFramework.helpers.resourceLocator;
import testFramework.objects.W3cLinkChecker;

import java.time.Duration;
import java.util.List;

import static testFramework.helpers.DateHelpers.humanReadableDuration;

public class PageLinkCheck {
    final Duration tout = Duration.ofSeconds(60 * 3); // the link checker can be quite slow

    private W3cLinkChecker w3cLinkChecker;
    private String url;

    @Given("the w3c link checker reviews the file {string}")
    public void theW3CLinkCheckerReviewsTheFile(String urlOfFile) {
        url = resourceLocator.interpretURL(urlOfFile);
        try {
            w3cLinkChecker = new W3cLinkChecker(url, tout);
        } catch (TimeoutException e) {
            Assert.fail("Failed to find results from:" + url + ": in " + humanReadableDuration(tout));
        }
    }

    @Then("the w3c link checker reports compliance")
    public void theW3CLinkCheckerReportsCompliance(DataTable dataTable) {
        // this is a list of strings (preferably domain names) that should be ignored
        List<String> possibleExceptions = dataTable.asList(String.class);

        Assert.assertTrue("Links on page :" + url + ": appear broken",
                w3cLinkChecker.fileValidates(possibleExceptions.subList(1, possibleExceptions.size()))
        );
    }
}
