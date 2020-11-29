package testFramework.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebElements {
    /**
     * Use this when you know that the thing exists, or not, and you don't want Selenium to use the implicit wait
     *
     * @param el      - the element to start the search from
     * @param locator - some sort of By, it could get 1 item, or many
     * @return - true, if it exists
     */
    public static boolean elementExists(WebElement el, By locator) {
        Context.defaultDriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        boolean exists = el.findElements(locator).size() != 0;
        Context.defaultDriver.manage().timeouts().implicitlyWait(Context.implicitWait, TimeUnit.SECONDS);
        return exists;
    }

    /**
     * Default the ait to the one set in the Context of the test
     *
     * @param driver            - which WebDriver you want to use
     * @param diagnosticElement - This is the element that you are hoping to eventually find
     */
    public static void waitForPresence(WebDriver driver, By diagnosticElement) {
        waitForPresence(driver, diagnosticElement, Context.pageLoadWait);
    }

    /**
     * @param driver            - which WebDriver you want to use
     * @param diagnosticElement - This is the element that you are hoping to eventually find
     * @param pageLoadWait      - in seconds
     */
    public static void waitForPresence(WebDriver driver, By diagnosticElement, Integer pageLoadWait) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(pageLoadWait));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(diagnosticElement));
    }
}