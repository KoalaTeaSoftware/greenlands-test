package testFramework.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;
import testFramework.helpers.Reports;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Things that all web pages contain / can do
 */
public class WebPageObj {
    public final By firstHeaderLocator = By.tagName("H1");

    public final WebDriver myDriver;

    public WebPageObj(WebDriver driver, By diagnosticLocator) {
        this.myDriver = driver;
        awaitReadiness(diagnosticLocator);
    }

    public WebPageObj(WebDriver driver) {
        this.myDriver = driver;
        awaitReadiness(By.tagName("BODY"));
    }

    private void awaitReadiness(By diagnosticElement) {
        // Selenium is supposed to do this, but let's do it too
        waitForJavaScriptReadyStateComplete(Context.pageLoadWait);

        new WebDriverWait(
                Context.defaultDriver, Duration.ofSeconds(Context.pageLoadWait)
        ).until(
                // use the 'presence', i.e. is the element actually in the DOM - it may not be visible
                ExpectedConditions.presenceOfElementLocated(diagnosticElement)
        );

    }

    public String readPageTitle() { return myDriver.getTitle(); }


    /**
     * @param tabIndex - staring at zero
     * @return the contents of the title tag for the indexed tab
     * @throws IndexOutOfBoundsException - if you ask for a tab that is not there
     */
    public String readPageTitle(int tabIndex) throws IndexOutOfBoundsException {
        String newTitle;
        List<String> browserTabs = new ArrayList<>(myDriver.getWindowHandles());

        myDriver.switchTo().window(browserTabs.get(tabIndex));

        newTitle = myDriver.getTitle();
        myDriver.close();
        myDriver.switchTo().window(browserTabs.get(0));

        return newTitle;
    }

    public String readFirstHeader() { return myDriver.findElement(firstHeaderLocator).getText(); }

    public void waitForTitleToBe(String expectedTitle) {
        WebDriverWait webDriverWait = new WebDriverWait(myDriver, Duration.ofSeconds(Context.pageLoadWait));
        webDriverWait.until(ExpectedConditions.titleIs(expectedTitle));
    }

    public void waitForFirstHeaderToBe(String expectedTitle) {
        WebDriverWait webDriverWait = new WebDriverWait(myDriver, Duration.ofSeconds(Context.pageLoadWait));
        webDriverWait.until(ExpectedConditions.textToBe(firstHeaderLocator, expectedTitle));
    }

    public URL readLocation() throws MalformedURLException {
        return new URL(myDriver.getCurrentUrl());
    }

    /**
     * This can be useful if you know that some JavaScript is going to do something
     *
     * @param maxWaitSeconds -
     */
    public void waitForJavaScriptReadyStateComplete(int maxWaitSeconds) {
        if (maxWaitSeconds == 0)
            return; // don't even create the executor

        String state = "";
        JavascriptExecutor js = (JavascriptExecutor) Context.defaultActor.getDriver();

        if (js != null)
            for (int i = 0; i < maxWaitSeconds; i++) {
                try {
                    state = js.executeScript("return document.readyState").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    Reports.writeToHtmlReport("Failed to get document state " + e.getMessage());
                    // Right at the beginning, if the browser has got nothing yet.
                    // we may hit "org.openqa.selenium.JavascriptException: javascript error: Cannot read property 'outerHTML' of null"
                    // In this case, we do not stop waiting.
                }
                if (state.equals("complete")) {
                    return;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                        // don't care
                    }
                }
            }
        else {
            Reports.writeToHtmlReport("[warning] Unable to execute JavaScript to determine if page has loaded");
        }
    }

    /**
     * Ask the browser if it actually managed to retrieve and draw the image
     *
     * @param imgTag - the web element that is the img tag
     * @return - true => it seems satisfactory
     */
    public boolean browserShowsImage(WebElement imgTag) {
        Object result = ((JavascriptExecutor) myDriver).executeScript(
                "return arguments[0].complete && " +
                        "typeof arguments[0].naturalWidth != \"undefined\" && " +
                        "arguments[0].naturalWidth > 0", imgTag);

        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        return false;
    }
}
