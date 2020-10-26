package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.objects.WebPageObj;

import java.time.Duration;

/**
 * Definitions here are all those that are visible / usable on all greenlands pages
 * Also, wait for the last thing to be part of the DOM
 */
public class GreenlandsCommonPage extends WebPageObj {

    @SuppressWarnings("FieldCanBeLocal")
    private final By lastElement = By.id("newsletterSignUp");

    public GreenlandsCommonPage(WebDriver driver) {
        super(driver);

        // the newsletterSignUp modal is at the bottom of all of the pages, its presence strongly implies that the page has been completely drawn
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(lastElement));
    }

    //    public void goToBottomOfPage() {
    //        myDriver.findElement(By.tagName("BODY")).sendKeys("" + Keys.PAGE_DOWN + Keys.PAGE_DOWN + Keys.PAGE_DOWN);
    //    }

}
