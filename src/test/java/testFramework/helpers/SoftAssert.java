package testFramework.helpers;

import org.junit.Assert;

public class SoftAssert {
    private boolean failureHasBeenEncountered;

    public SoftAssert() {
        failureHasBeenEncountered = false;
    }

    public void assertTrue(boolean assertion, String failureMessage) {
        assertTrue(assertion, failureMessage, false);
    }

    public void assertTrue(boolean assertion, String failureMessage, boolean withScreenshot) {
        if (!assertion) {
            failureHasBeenEncountered = true;
            if (withScreenshot)
                Reports.writeScreenShotToHtmlReport(failureMessage);
            else
                Reports.writeToHtmlReport(failureMessage);

        }
    }

    public void assertAll() {
        assertAll("");
    }

    public void assertAll(String message) {
        if (failureHasBeenEncountered)
            Assert.fail(message);
    }
}
