package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import testFramework.objects.WebPageObj;

/**
 * Definitions here are all those that are visible / usable on all greenlands pages
 * Also, wait for the last thing to be part of the DOM
 */
public class GreenlandsCommonPage extends WebPageObj {

    public GreenlandsCommonPage(WebDriver driver) { super(driver, By.id("newsletterSignUp")); }

    public GreenlandsCommonPage(WebDriver driver, By diagnosticLocator) { super(driver, diagnosticLocator); }

}
