package testSuite.objects;

import org.openqa.selenium.By;
import testFramework.objects.HtmlPageObject;

/**
 * Definitions here are all those that are visible / usable on all greenlands pages
 * Also, wait for the last thing to be part of the DOM
 */
public class GreenlandsCommonPage extends HtmlPageObject {
    /**
     * in normal usage, we will be using the default driver and the default diagnostic locator, so make life easy for
     * everyone
     */
    //    public GreenlandsCommonPage() {
    //        super(By.id("policyLink"));
    //    }

    /**
     * Occasionally, we may need to specify a different diagnostic locator.
     *
     * @param diagnosticLocator - a By, the presence of which, tells if the page has been drawn
     */
    public GreenlandsCommonPage(By diagnosticLocator) {
        super(diagnosticLocator);
    }

}
