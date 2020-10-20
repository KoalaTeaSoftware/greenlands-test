package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.objects.WebPageObj;

import java.time.Duration;

public class GreenlandsCommonPage extends WebPageObj {
    @FindBy(id = "contactLink")
    public WebElement contactLink;
    @FindBy(id = "membershipLink")
    public WebElement membershipLink;
    @FindBy(id = "policyLink")
    public WebElement policyLink;
    @FindBy(id = "signUp")
    public WebElement membershipModal;

    private final WebDriver myDriver;

    public GreenlandsCommonPage(WebDriver driver) {
        super(driver);
        myDriver = driver;

        // the sign up modal is at the bottom of all of the pages, its presence strongly implies that the page has been completely drawn
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("signUp")));

        PageFactory.initElements(myDriver, this);
    }

    public void goToBottomOfPage() {
        myDriver.findElement(By.tagName("BODY")).sendKeys(Keys.chord(Keys.CONTROL, Keys.DOWN));
    }
}
