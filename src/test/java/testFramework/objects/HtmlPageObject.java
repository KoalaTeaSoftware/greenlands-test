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

public class HtmlPageObject {
    public final WebDriver myDriver;

    public HtmlPageObject() {
        this.myDriver = Context.defaultDriver;
        // OK, Selenium is supposed to do this, but let's make it specific
        waitForJavaScriptReadyStateComplete(Context.pageLoadWait);
        waitTillDrawn(By.tagName("BODY"));
    }

    public HtmlPageObject(By diagnostic) {
        this.myDriver = Context.defaultDriver;
        waitForJavaScriptReadyStateComplete(Context.pageLoadWait);
        waitTillDrawn(diagnostic);
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


    public String readPageTitle() {
        return myDriver.getTitle();
    }

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
     * an explicit wait for the visibility of something that you know is slow for example
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

        boolean loaded = false; // unless we can convert it to true
        if (result instanceof Boolean) {
            loaded = (Boolean) result;
        }
        return loaded;
    }

    public List<WebElement> listImgTags() {
        return myDriver.findElements(By.tagName("IMG"));
    }
}