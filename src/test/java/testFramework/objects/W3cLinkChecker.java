package testFramework.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;
import testFramework.helpers.Reports;

import java.time.Duration;
import java.util.List;

public class W3cLinkChecker {

    /**
     * It is best to aim this directly at the single files that you create.
     * For example, Bootstrap's css invokes error messages (false negatives?) from this tester.
     *
     * @param urlOfCssFile - make it a single file.Scheme is not necessary
     */
    public W3cLinkChecker(String urlOfCssFile, Duration tout) throws TimeoutException {
        String sut = "https://validator.w3.org/checklink?uri=";
        sut += urlOfCssFile;
        sut += "&summary=on&hide_type=all&depth=&check=Check";

        Context.defaultActor.getResource(sut);

        new WebDriverWait(Context.defaultDriver, tout).until(ExpectedConditions.presenceOfElementLocated(By.tagName("H3")));
    }

    /**
     * @param possibleExceptions - if any error can be found to be in this list of exceptions, then ignore it
     * @return - opinion on whether the file validates (taking into account the exceptions)
     */
    public Boolean fileValidates(List<String> possibleExceptions) {
        // the first h3 tells you the result
        if (Context.defaultDriver.findElement(By.tagName("h3")).getText().toLowerCase().contains("broken links")) {
            // Report it, but the SUT may not be a fault in the SUT
            Reports.writeToHtmlReport("Found evidence of broken links (an H3 saying just that)");
            Reports.writeToHtmlReport(Context.defaultDriver.findElement(By.xpath("//dl[@class='report']")).getAttribute("innerHTML"));
           /*
            Here is an example of the HTML that you might see:
            <dl class="report">
                <dt id="d1code_200">
                    <span class="err_type"><img src="/checklink/images/info_icons/error.png" alt="error"></span>
                    <span class="msg_loc">Line: 158</span>
                    <a href="https://s7.addthis.com/js/300/addthis_widget.js">https://s7.addthis.com/js/300/addthis_widget.js</a>
                </dt>
                <dd class="responsecode"><strong>Status</strong>:  200 OK</dd>
                <dd class="message_explanation"><p>Some of the links to this resource point to broken URI fragments (such as index.html#fragment). </p></dd>
                <dd>Broken fragments:
                    <ul>
                        <li>https://s7.addthis.com/js/300/addthis_widget.js<em>#pubid=ra-5e0ece84a60a07d6</em> (line 158)</li>
                    </ul>
                </dd>
            </dl>
             */
            List<WebElement> ers = Context.defaultDriver.findElements(By.xpath("//dl[@class='report']//dt//a"));
            loopOverErrors:
            for (WebElement thisErr : ers) {
                String thisErrorLink = thisErr.getAttribute("href").toLowerCase();
                for (String oneException : possibleExceptions) {
                    if (thisErrorLink.contains(oneException)) {
                        Reports.writeToHtmlReport(
                                String.format("This error's link %s is one of the exceptions %s", thisErrorLink, oneException)
                        );
                        // as weh have found an exception for this error, go on to the next error
                        continue loopOverErrors;
                    }
                }
                // we have fallen off the end of the exception list, so this error is a fatal error
                return false;
            }
            // If we have arrived here, all errors match exceptions, so call it valid
            return true;
        }
        // Didn't find any 'broken links' notification, to double-check, hunt for the p that specifically indicates
        // success
        for (WebElement p : Context.defaultDriver.findElements(By.tagName("p"))) {
            if (p.getText().equalsIgnoreCase("Valid links!"))
                return true;
        }

        // failing anything good, default to failure
        Reports.writeToHtmlReport("Failed to find evidence of valid links (a P saying just that)");
        return false;
    }
}