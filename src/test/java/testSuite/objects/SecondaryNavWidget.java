package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SecondaryNavWidget {
    // once the object has been created, it will always use the same driver
    // I can't imagine any scenario when it will not be the default, but make it plain by putting it here
    private final WebDriver myDriver;
    // Locators
    @FindBy(id = "tailNav")
    public WebElement tailNavBlock;
    @FindBy(id = "contactLink")
    public WebElement contactLink;
    @FindBy(id = "membershipLink")
    public WebElement membershipLink;
    @FindBy(id = "policyLink")
    public WebElement policyLink;

    // Actions

    public SecondaryNavWidget(WebDriver driver) {
        myDriver = driver;
        PageFactory.initElements(myDriver, this);

        WebDriverWait webDriverWait = new WebDriverWait(myDriver, Duration.ofSeconds(40));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(policyLink));
    }

    // Housekeeping

    private By navLink(String name) {
        return By.xpath("//*[@id='tailNav']//*[@class='nav-item']//A[normalize-space(text())='" + name + "']");
    }

    /**
     * Find and click on a secondary link which has the text given.
     *
     * @param name - normalised string that you expect to see for this link. Case sensitive
     */
    public void followLink(String name) {
        myDriver.findElement(navLink(name)).click();
    }
}
