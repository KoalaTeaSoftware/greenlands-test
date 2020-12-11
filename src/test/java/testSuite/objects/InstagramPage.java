package testSuite.objects;

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
}
