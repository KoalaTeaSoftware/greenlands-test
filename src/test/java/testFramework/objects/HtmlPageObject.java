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
import java.util.List;

public class HtmlPageObject {
    public final WebDriver myDriver = Context.defaultDriver;

    public HtmlPageObject() {
        // OK, Selenium is supposed to do this, but let's make it specific
        waitTillDrawn(By.tagName("BODY"));
        waitForJavaScriptReadyStateComplete(Context.pageLoadWait);
    }

    /**
     * This constructor will wait until the diagnostic element is drawn (probably meaning it is present) and then wait
     * until the browser report that the page state is complete.
     * This should be efficacious with the abominably slow pages (e.g. those relying on wordpress)
     *
     * @param diagnostic - a WebElement that, if present, strongly implies that the page is of the right kind
     */
    public HtmlPageObject(By diagnostic) {
        // wait until the diagnostic element is present (the page could be horribly slow)
        waitTillDrawn(diagnostic);
        // Wait until the browser thinks that it has finished with this (new?) page
        waitForJavaScriptReadyStateComplete(Context.pageLoadWait);
    }

    /**
     * Most of the time, the operations will be going on in one tab. This will return the title of that tab
     *
     * @return - the title of tab zero
     */
    public String readPageTitle() {
        return myDriver.getTitle();
    }

    /**
     * Does not read the current one, but waits for it to be what you want it to be
     *
     * @param expectedTitle
     */
    public void waitForPageTitleToBe(String expectedTitle) {
        new WebDriverWait(
                Context.defaultDriver,
                Duration.ofSeconds(Context.pageLoadWait))
                .until(ExpectedConditions.titleIs(expectedTitle));
    }

    /**
     * Does not read the current one, but waits for it to be what you want it to be
     *
     * @param expectedTitle
     */
    public void waitForPageTitleToContain(String expectedTitle) {
        new WebDriverWait(
                Context.defaultDriver,
                Duration.ofSeconds(Context.pageLoadWait))
                .until(ExpectedConditions.titleContains(expectedTitle));
    }

    public String readFirstHeader() {
        return myDriver.findElement(By.tagName("H1")).getText();
    }

    public URL readCurrentLocation() {
        URL url = null;
        try {
            url = new URL(myDriver.getCurrentUrl());
        } catch (MalformedURLException ignore) {
            // surely, this never throws a malformed URL?
        }
        return url;
    }


    /**
     * When looking at web pages, the implicit wait may not be sufficient,
     * so this explicitly asks the browser if it thinks it has got everything
     * <p>
     * Even this may be less help than you like, so it may be good to override it in your own page object definitions with
     * an explicit wait for the visibility of something that you know is slow for example:
     * <p>
     * WebDriverWait wait = new WebDriverWait(driver, 10);
     * WebElement elem = driver.findElement(By.id("diagnosticElement"));
     * wait.until(ExpectedConditions.visibilityOf(elem));
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
                    Reports.writeToHtmlReport("Failed to get document state " + e.getMessage() + ", but continuing to" +
                            " try");
                    // Right at the beginning, if the browser has got nothing yet.
                    // we may hit "org.openqa.selenium.JavascriptException: javascript error: Cannot read property 'outerHTML' of null"
                    // In this case, we do not stop waiting.
                }
                if (state.equals("complete")) {
                    return;
                } else {
                    try {
                        System.out.println("Still waiting for the JavaScript to say complete. It is currently " + state + ".");
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
        } else {
            return false;
        }
    }

    public List<WebElement> listImgTags() {
        return myDriver.findElements(By.tagName("IMG"));
    }

    private void waitTillDrawn(By diagnostic) {
        new WebDriverWait(
                Context.defaultDriver,
                Duration.ofSeconds(Context.pageLoadWait))
                // use the 'presence', i.e. is the element actually in the DOM - it may not be visible
                .until(
                        ExpectedConditions.presenceOfElementLocated(diagnostic)
                );
    }

}
