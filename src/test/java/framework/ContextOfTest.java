package framework;

import framework.actors.Actor;
import framework.helpers.ConfigReader;
import framework.helpers.HtmlReport;
import org.junit.Assert;

import java.io.IOException;

/**
 * This class holds all data that is shared over the life of the test run (i.e. broader scope even than features)
 * As of now, this is just reading the configuration files that will hold data relevant to the whole test run
 */
public class ContextOfTest {
    // these fields are static so that the test steps (which are static) can gain access to them
    public static ConfigReader sutConfiguration;
    public static ConfigReader testConfiguration;

    public static Actor.ActorType defaultBrowserType;
    public static String defaultBrowserLocation;
    public static String defaultUiPageTitleString;
    public static int pageLoadWait;
    public static int implicitWait;


    public static boolean catchBrowserLogs = false;

    /**
     * ***********************************************************************************************************************
     * The following private declaration of one of my kind and the subsequent private constructor ensure that I am a Singleton
     * Initialisation of me does not work if it is in the constructor, it has to be here
     */
    private static ContextOfTest me = new ContextOfTest();

    private ContextOfTest() {
        try {
            // Read the config files
            testConfiguration = new ConfigReader("src/test/configuration/testFramework.properties");
            sutConfiguration = new ConfigReader(testConfiguration.getProperty("sutConfigPath"));
            // this system property is required by the fancy HTML reporter from https://gitlab.com/monochromata-de/cucumber-reporting-plugin
        } catch (IOException | NoSuchFieldException e) {
            e.printStackTrace();
            Assert.fail("Problem loading configuration for the test");
        }

        try {
            System.setProperty("cucumber.reporting.config.file", testConfiguration.getProperty("reportConfigFile"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Assert.fail("The report configuration file location must be defined the the test configuration");
        }

        try {
            defaultBrowserType = Actor.ActorType.valueOf(testConfiguration.getProperty("defaultBrowser").toUpperCase());
        } catch (NoSuchFieldException e) {
            defaultBrowserType = null;
        }

        try {
            defaultBrowserLocation = sutConfiguration.getProperty("defaultUiProtocol") + "://";
        } catch (NoSuchFieldException e) {
            defaultBrowserLocation = "";
        }

        try {
            defaultBrowserLocation += sutConfiguration.getProperty("defaultUiDomainName");
        } catch (NoSuchFieldException e) {
            HtmlReport.writeToHtmlReport("[info] As the default domain name was not defined, any default protocol will be ignored");
            defaultBrowserLocation = "";
        }

        try {
            defaultUiPageTitleString = sutConfiguration.getProperty("defaultUiPageTitleString");
        } catch (NoSuchFieldException e) {
            defaultUiPageTitleString = "";
        }

        try {
            ContextOfTest.catchBrowserLogs = ContextOfTest.testConfiguration.getProperty("catchBrowserLogs").equalsIgnoreCase("true");
        } catch (NoSuchFieldException e) {
            ContextOfTest.catchBrowserLogs = false;
        }

        try {
            implicitWait = Integer.parseInt(ContextOfTest.testConfiguration.getProperty("implicitWaitTime"));
        } catch (NoSuchFieldException e) {
            implicitWait = 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Assert.fail("Unable to understand the implicitWaitTime test configuration value");
        }

        try {
            pageLoadWait = Integer.valueOf(ContextOfTest.testConfiguration.getProperty("waitForPageLoad"));
        } catch (NoSuchFieldException e) {
            System.out.println("[info] Defaulting Page load wait to zero");
            pageLoadWait = 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Assert.fail("Unable to understand the waitForPageLoad test configuration value");
        }
    }
}
