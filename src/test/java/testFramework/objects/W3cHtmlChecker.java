package testFramework.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;

import java.time.Duration;

public class W3cHtmlChecker {
    @SuppressWarnings("FieldCanBeLocal")
    private final By entryFieldLocator = By.id("uri");
    @SuppressWarnings("FieldCanBeLocal")
    private final By checkButtonLocator = By.className("submit");
    @SuppressWarnings("FieldCanBeLocal")
    private final By resultsBlockLocator = By.id("results");

    /**
     * It is best to aim this directly at the single files that you create.
     * For example, Bootstrap's css invokes error messages (false negatives?) from this tester.
     *
     * @param urlOfHtmlFile - make it a single file.Scheme is not necessary
     */
    public W3cHtmlChecker(String urlOfHtmlFile, Duration tout) {
        //        try {
        //            String sut = "https://html5.validator.nu/?doc=";
        //            sut += URLEncoder.encode(urlOfHtmlFile, "UTF-8");
        //            sut += "&parser=html";

        Context.defaultDriver.get("https://validator.w3.org/");
        // wait until wee see the check button
        @SuppressWarnings("unused")
        WebPageObj validator = new WebPageObj(Context.defaultDriver, checkButtonLocator);

        Context.defaultDriver.findElement(entryFieldLocator).sendKeys(urlOfHtmlFile);

        Context.defaultDriver.findElement(checkButtonLocator).click();
        new WebDriverWait(Context.defaultDriver, tout).
                until(ExpectedConditions.presenceOfElementLocated(By.className("details"))
                );
        //        } catch (UnsupportedEncodingException e) {
        //            Assert.fail("Unable to encode URL" + e.getMessage());
        //        }
    }

    /**
     * On both the success and failure pages, the first h3 tells you the result
     *
     * @return - whether it contains text that indicates success, or failure
     */
    public Boolean fileValidates() {
        try {
            String msg = Context.defaultDriver.findElement(By.className("success")).getText().toLowerCase();
            if (msg.contains("no error"))
                return true;
        } catch (NoSuchElementException ignored) {
        }
        return false;
    }

    public String readResults() {
        return Context.defaultDriver.findElement(resultsBlockLocator).getAttribute("innerHTML");
    }
}