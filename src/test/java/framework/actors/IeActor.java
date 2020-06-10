package framework.actors;

import framework.ContextOfTest;
import framework.helpers.DateHelpers;
import io.cucumber.java.Scenario;
import org.junit.Assert;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class IeActor extends Actor {

    private InternetExplorerDriverService service;

    /**
     * IE refuses to close windows. This is quite widely reported, and
     * there appears to be no clear idea of exactly why.
     * This blunderbus has been suggested in a few places and is a workable option
     */
    @Override
    public void closeDriver() {
        if (null != driver) {
            System.out.println("[info] Closing IE driver");
            driver.quit(); // try it, just in case they fix the bug
            driver = null; // Essential to do this, else you keep getting the dead driver
            try {
                Runtime.getRuntime().exec("taskkill /F /T /IM iexplore.exe");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            // maybe something has gone wrong with the framework?
            System.out.println("[info] No driver to close");
    }

    @Override
    protected void startService() {
        if (null == service) {
            System.out.println("[info] Creating a Driver Service for IE");
            // i.e. Lazy Instantiation of the Service
            try {
                String driverPath = ContextOfTest.testConfiguration.getProperty("ieDriverPath");

                String logLoc = System.getProperty("user.dir");
                logLoc += File.separator + ContextOfTest.testConfiguration.getProperty("driverLogLocation");
                logLoc += File.separator + "InternetExplorer-" + DateHelpers.uniqueFileName() + ".txt";

                service = new InternetExplorerDriverService.Builder()
                        .usingDriverExecutable(new File(driverPath))
                        .usingAnyFreePort()
                        .withLogFile(new File(logLoc))
                        .withLogLevel(InternetExplorerDriverLogLevel.DEBUG)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail();
            }
        }
    }

    // NB: this is unused at the moment,but is here for completeness
    @Override
    protected void stopService() {
        if (null != service && service.isRunning()) {
            System.out.println("[info] Stopping the Driver Service for IE");
            service.stop();
        }
    }

    @Override
    public void createDriver() {
        System.out.println("[info] Creating a Web Driver for IE");

        DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
        cap.setCapability("platform", "WIN10");
        cap.setCapability("version", "11");
        cap.setCapability("browserName", "internet explorer");
        cap.setCapability("ignoreProtectedModeSettings", 1);
        cap.setCapability("nativeEvents", "false");
        cap.setCapability("ignoreZoomSetting", true);
        cap.setCapability("requireWindowFocus", "true");
//        cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

        driver = new InternetExplorerDriver(service, cap);

        driver.manage().timeouts().implicitlyWait(ContextOfTest.implicitWait, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }

    /**
     * For this type of Actor, replace the one already defined (it is good for browsers)
     * Just dump the whole thing into the HTML report
     * <p>
     * Again, this is not intended for direct use. User of the framework should use HtmlReport.embedScreenshot()
     *
     * @param scenario - found in the scenario context
     * @param label    - what you want to se written in the report
     */
    @Override
    public void embedScreenShot(Scenario scenario, String label) {
        scenario.write(label);
        scenario.write("To Do: IE fails to tke screenshots");

//        TakesScreenshot ts = (TakesScreenshot) ContextOfScenario.actor.getDriver();
//        byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            // whilst this is complaining the embed is deprecated, it still works
            scenario.embed(imageInByte, "image/png");
        } catch (AWTException | IOException e) {
            e.printStackTrace();
            scenario.write("Failed to Capture a screenshot");
        }

        /* for IE
        BufferedImage image = new Robot().createScreenCapture(new    Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
ImageIO.write(image, "png", new File("/screenshot.png"));
         */

    }
}
