package testFramework.actors;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import testFramework.Context;

import java.util.concurrent.TimeUnit;

public class FirefoxActor extends Actor {
    private GeckoDriverService service;

    //    @Override
    //    protected void stopService() {
    //        if (null != service && service.isRunning()) {
    //            System.out.println("[info] Stopping the Driver Service for Firefox");
    //            service.stop();
    //        }
    //
    //    }

    @Override
    protected void startService() {
        WebDriverManager.firefoxdriver().setup();
        //        if (service == null) {
        //            System.out.println("[info] Creating a Driver Service for Firefox");
        //            try {
        ////                String logLoc = System.getProperty("user.dir");
        ////                logLoc += File.separator + ContextOfTest.testConfiguration.getProperty("driverLogLocation");
        ////                logLoc += File.separator + "Firefox-" + DateHelpers.uniqueFileName() + ".txt";
        //
        //                String driverPath = Context.testConfiguration.getProperty("firefoxDriverPath");
        //
        //                FirefoxOptions options = new FirefoxOptions();
        //                options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        //
        //                service = new GeckoDriverService.Builder()
        //                        .usingDriverExecutable(new File(driverPath))
        //                        .usingAnyFreePort()
        //                        //                        .withLogFile(new File(logLoc))
        //                        .build();
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //                Assert.fail();
        //            }
        //        }
    }

    @Override
    public void createDriver() {
        System.out.println("[info] Creating a Web Driver for Firefox");

        boolean maximise = false;
        boolean headless = false;

        try {
            if (Context.testConfiguration.getProperty("headless").equalsIgnoreCase("true"))
                headless = true;
        } catch (NoSuchFieldException e) {
            // leave it false
        }
        try {
            if (Context.testConfiguration.getProperty("windowMaximize").equalsIgnoreCase("true"))
                maximise = true;
        } catch (NoSuchFieldException e) {
            // leave it false
        }

        // https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/firefox/FirefoxOptions.html
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(headless);

        driver = new FirefoxDriver(options);
        //        driver = new FirefoxDriver(service, options);

        driver.manage().timeouts().implicitlyWait(Context.implicitWait, TimeUnit.SECONDS);

        if (maximise)
            driver.manage().window().maximize();

    }

}
