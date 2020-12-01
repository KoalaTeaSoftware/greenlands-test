package testFramework.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;

import java.time.Duration;

public class W3cHtmlChecker {
    //    Context.defaultDriver.findElement(By.xpath("/html/body/ol/li[@class='error']/ancestor::ol")).getAttribute("outerHTML")
    private final By errorList = By.xpath("//li[@class='error']/ancestor::ol");
    private final By warningList = By.xpath("//li[contains(@class,'warning')]/ancestor::ol");

    /**
     * It is best to aim this directly at the single files that you create.
     * For example, Bootstrap's css invokes error messages (false negatives?) from this tester.
     *
     * @param urlOfHtmlFile - make it a single file.Scheme is not necessary
     */
    public W3cHtmlChecker(String urlOfHtmlFile, Duration tout) {
        String sut = "https://validator.w3.org/nu/?doc=" + urlOfHtmlFile;

        Context.defaultActor.getResource(sut);

        new WebDriverWait(Context.defaultDriver, tout).
                until(ExpectedConditions.presenceOfElementLocated(By.className("details"))
                );
    }

    /**
     * To be called once the checker has reviewed the subject file
     * <p>
     * On both the success and failure pages, the first h3 tells you the result
     *
     * @return - whether it contains text that indicates success, or failure
     */
    public Boolean fileValidates() {
        try {
            // look for a success paragraph that contains some text
            Context.defaultDriver.findElement(By.className("success")).getText();
            return true;
        } catch (NoSuchElementException e) {
            // if there is no such paragraph then consider it as a failure
            return false;
        }
    }

    /**
     * @return - any warnings, and any errors. If there are none to be found, then am empty list is given. This is
     * probably a fault somewhere
     */
    public String getErrorList() {
        String result = "";
        try {
            result += Context.defaultDriver.findElement(warningList).getAttribute("outerHTML");
        } catch (NoSuchElementException ignore) { }
        try {
            result += Context.defaultDriver.findElement(errorList).getAttribute("outerHTML");
        } catch (NoSuchElementException ignore) { }
        return result;
    }
}