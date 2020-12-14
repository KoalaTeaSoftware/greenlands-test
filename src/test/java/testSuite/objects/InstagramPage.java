package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;
import testFramework.objects.HtmlPageObject;

import java.time.Duration;

public class InstagramPage extends HtmlPageObject {
    // the instagram page is made entirely using JavaScript (React), so our conventional waiting can not be used
    // instead, the best we can do is wait until the page title is like we hope it to be
    public InstagramPage() {
        super();
        new WebDriverWait(
                Context.defaultDriver,
                Duration.ofSeconds(Context.pageLoadWait))
                // the string it is looking for is taken from an actual sample, and is assumed to be case sensitive
                .until(
                        ExpectedConditions.titleContains("Instagram")
                );
    }

    /**
     * @return - true means that it found the tell-tale words, false means it did not - this could be less than reliable
     */
    public boolean botWarningPresent() {
        try {
            Context.defaultDriver.findElements(By.xpath("//p[@text='Please wait a few minutes before you try again']"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

}
