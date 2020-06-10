package framework.actors;

import framework.ContextOfScenario;
import io.cucumber.java.Scenario;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public abstract class Actor {

    protected CloseableHttpResponse httpResponse;
    protected WebDriver driver;

    /**
     * Constructor for the generic type.
     * Sets up some environment configuration, so as to keep this stuff in one place
     */
    public Actor() {
        httpResponse = null;
    }


    /**
     * Get the driver so you can do all of the interesting stuff that Selenium lets you do
     * If necessary, it will make a driver for you
     *
     * @return - A working web driver
     */
    public WebDriver getDriver() {
        if (null == driver) {
            startService();
            createDriver();
        }
        return driver;
    }

    /**
     * This is only intended for use by the framework to allow it to isolate each Scenario
     * It should not be used elsewhere, unless under extraordinary circumstances
     */
    public void closeDriver() {
        if (null != driver) {
            System.out.println("[info] Closing driver");
            driver.quit();
            driver = null; // Essential to do this, else you keep getting the dead driver
        } else
            // maybe something has gone wrong with the framework?
            System.out.println("[info] No driver to close");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Most of the different types of actor will have to do a different something at each of these points in the in the test lifecycle
    // So force them to be created

    /**
     * @param fullURL - of the page that you want to see. Can be relative
     */
    public void getResource(String fullURL) {
        getDriver().get(fullURL);
    }

    /**
     * Defined within the actor because the different types of actor behave in different ways
     * This definition is good for the various browsers and has to be overridden for the API and Appium actors
     * <p>
     * Do not directly use this, always use HtmlReport.embedScreenshot()
     *
     * @param scenario - found in the scenario context
     * @param label    - what you want to se written in the report
     */
    public void embedScreenShot(Scenario scenario, String label) {
        scenario.write(label);

        TakesScreenshot ts = (TakesScreenshot) ContextOfScenario.actor.getDriver();
        byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

        // whilst this is complaining the embed is deprecated, it still works
        scenario.embed(screenshot, "image/png");

        /* for IE
        BufferedImage image = new Robot().createScreenCapture(new    Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
ImageIO.write(image, "png", new File("/screenshot.png"));
         */
    }

    /**
     * Use this with the API Actor is the operating Actor, so that the response can be analysed
     * Otherwise (e.g. for th browser actors) the response will be null (initial value of the httpResponse)
     *
     * @return
     */
    public CloseableHttpResponse getResponse() {
        return httpResponse;
    }

    protected abstract void startService();

    protected abstract void stopService();

    protected abstract void createDriver();

    /**
     * Choose one of these when you instantiate your actor
     */
    public enum ActorType {
        FIREFOX,
        CHROME,
        INTERNET_EXPLORER,
        SAFARI,
        ANDROID, // actually using android to do things, ie an app
        IOS, // also an app
        API
    }
}
