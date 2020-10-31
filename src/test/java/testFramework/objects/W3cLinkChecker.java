package testFramework.objects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;
import testFramework.helpers.Reports;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;

public class W3cLinkChecker {
    /**
     * It is best to aim this directly at the single files that you create.
     * For example, Bootstrap's css invokes error messages (false negatives?) from this tester.
     *
     * @param urlOfSubject - make it a single file.Scheme is not necessary
     */
    public W3cLinkChecker(String urlOfSubject, Duration tout) throws TimeoutException {
        try {
            String sut = "https://validator.w3.org/checklink?uri=";
            sut += URLEncoder.encode(urlOfSubject, "UTF-8");
            sut += "&summary=on&hide_type=all&depth=&check=Check";

            Context.defaultActor.getResource(sut);

            new WebDriverWait(Context.defaultDriver, tout).until(ExpectedConditions.presenceOfElementLocated(By.tagName("H3")));
        } catch (UnsupportedEncodingException e) {
            Assert.fail("Unable to encode URL" + e.getMessage());
        } catch (TimeoutException e) {
            Assert.fail("Unable Find results within the time " + tout + " " + e.getMessage());
        }
    }

    /**
     * @return - whether it contains text that indicates success, or failure
     */
    public Boolean fileValidates() {
        // the first h3 tells you the result
        if (Context.defaultDriver.findElement(By.tagName("h3")).getText().toLowerCase().contains("broken links")) {
            // it definitely says there is a problem
            Reports.writeToHtmlReport("Found evidence of broken links (an H3 saying just that)");
            Reports.writeToHtmlReport(Context.defaultDriver.findElement(By.tagName("dl")).getAttribute("innerHTML"));
            return false;
        }
        // otherwise, hunt for the p that specifically indicates success
        for (WebElement p : Context.defaultDriver.findElements(By.tagName("p"))) {
            if (p.getText().equalsIgnoreCase("Valid links!"))
                return true;
        }
        // failing anything good, default to failure
        Reports.writeToHtmlReport("Failed to find evidence of success (a P saying just that)");
        return false;
    }
}