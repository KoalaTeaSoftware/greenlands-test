package framework.objects;

import framework.ContextOfScenario;
import framework.ContextOfTest;
import framework.helpers.HtmlReport;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * This gives you access to many basic features common to all HTML pages in all browsers
 */
public class HtmlPage {
    private WebDriver myDriver = null;

    public HtmlPage(WebDriver driver) {
        this.myDriver = driver;

        awaitPageLoad(ContextOfTest.pageLoadWait);
        // this would be where you would initialise the page factory
        // PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return myDriver.getTitle();
    }

    /**
     * When looking at web pages, the implicit wait may not be sufficient,
     * so explicitly ask the browser if it thinks it has got everything
     * <p>
     * Even this may be less help than you like, so it may be good to override it in your own page object definitions with with
     * an explicit wait for the visibility of something that you know is slow
     * for example
     * <p>
     * WebDriverWait wait = new WebDriverWait(driver, 10);
     * WebElement elem = driver.findElement(By.id("diagnosticElement"));
     * wait.until(ExpectedConditions.visibilityOf(elem));
     *
     * @param maxWaitSeconds -
     */
    public void awaitPageLoad(int maxWaitSeconds) {
        if (maxWaitSeconds == 0)
            return; // don't even create the executor

        String state = "";
        JavascriptExecutor js = (JavascriptExecutor) ContextOfScenario.actor.getDriver();

        if (js != null)
            for (int i = 0; i < maxWaitSeconds; i++) {
                try {
                    state = js.executeScript("return document.readyState").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    HtmlReport.writeToHtmlReport("Failed to get document state " + e.getMessage());
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
            System.out.println("[warning] unable to execute JavaScript to determine if page has loaded");
            HtmlReport.writeToHtmlReport("[warning] unable to execute JavaScript to determine if page has loaded");
        }
    }

}
