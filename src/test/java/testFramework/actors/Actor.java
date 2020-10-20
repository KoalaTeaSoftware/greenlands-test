package testFramework.actors;

import org.openqa.selenium.WebDriver;

public abstract class Actor {

    protected WebDriver driver;

    public Actor() {
        startService();
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
            System.out.println("[Warning] No driver to close");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Most of the different types of actor will have to do a different something at each of these points in the in the test lifecycle
    // So force them to be created
    protected abstract void startService();

    protected abstract void createDriver();


    // --Commented out by Inspection START (11/10/2020 20:28):
    //    /**
    //     * This may never actually be called. The service may just fade away as the test dies
    //     */
    //    protected abstract void stopService();
    // --Commented out by Inspection STOP (11/10/2020 20:28)

    /**
     * All of the browsers will use this, but a different type of actor (e.g. an API shim) will want to override it.
     *
     * @param fullURL - complete address of the page that you want to see.
     */
    public void getResource(String fullURL) {
        getDriver().get(fullURL);
    }
}
