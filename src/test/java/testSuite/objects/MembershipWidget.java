package testSuite.objects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;
import testFramework.helpers.Reports;

import java.time.Duration;

public class MembershipWidget {
    // locators
    final By headerLocator = By.xpath("//*[@id='signUp']//*[@class='modal-title']");
    // Housekeeping
    // as the widget is not an extension of anything, store the driver.
    // I can't see why it would not be the default driver, but put it here, just tom ke it easier if it has to change
    private final WebDriver myDriver = Context.defaultDriver;
    @FindBy(id = "signUp")
    WebElement thisWidget;
    @FindBy(id = "emailAddress")
    WebElement emailAddressField;
    @FindBy(id = "pwd")
    WebElement passwordField;
    @FindBy(id = "logIn")
    WebElement logInButton;
    @FindBy(name = "signUp")
    WebElement signUpButton;
    @FindBy(name = "status")
    WebElement statusDisplay;
    @FindBy(xpath = "//*[id='signUp']//button[class='close']")
    WebElement closeButton;

    // Actions
    public MembershipWidget() {
        // prove to ourselves that there is actually such a thing in the DOM (hidden, or not)
        WebDriverWait webDriverWait = new WebDriverWait(myDriver, Duration.ofSeconds(Context.implicitWait));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(headerLocator));

        PageFactory.initElements(myDriver, this);
    }

    public boolean widgetIsVisible() {
        try {
            // You get occasional false failures unless you run it in debug mode,
            // so it must take a little while to draw it. Wait for it.
            WebDriverWait webDriverWait = new WebDriverWait(myDriver, Duration.ofSeconds(Context.implicitWait));
            webDriverWait.until(ExpectedConditions.visibilityOf(thisWidget));
        } catch (TimeoutException | NoSuchElementException e) {
            Reports.writeScreenShotToHtmlReport(
                    "The membership widget is not visible - IS THIS AN ERROR?");
            Reports.writeScreenShotToHtmlReport(e.getMessage());
            return false;
        }

        return thisWidget.getAttribute("class").contains("show"); // this may seem to be testing the same thing
    }

    public boolean widgetIsNotVisible() {
        // in this case do not wait to see if it comes visible after a wile, immediately give the decision
        return !thisWidget.getAttribute("class").contains("show");
    }
}
