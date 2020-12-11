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
        // It wll make an Instagram page first, the populate it with specific stuff
        // Separate it out into 2 like this to make it easier to debug

        // 2020-12-11 I am having to comment this out because I am finding that, randomly links occasionally fail to
        // work
        //        new WebDriverWait(
        //                Context.defaultDriver,
        //                Duration.ofSeconds(Context.pageLoadWait))
        //                // the string it is looking for is taken from an actual sample, and is assumed to be case sensitive
        //                .until(
        //                        ExpectedConditions.titleContains("Greenlands")
        //                );

    }
}
