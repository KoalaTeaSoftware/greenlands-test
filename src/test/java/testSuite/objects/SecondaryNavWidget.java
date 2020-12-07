package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.objects.HtmlPageObject;

import java.time.Duration;

public class SecondaryNavWidget extends HtmlPageObject {
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

    public SecondaryNavWidget() {
        super(new By.ById("tailNav"));
        PageFactory.initElements(myDriver, this);

        WebDriverWait webDriverWait = new WebDriverWait(myDriver, Duration.ofSeconds(40));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(policyLink));
    }

    // Housekeeping

    private By getNavlinkWithName(String name) {
        return By.xpath("//*[@id='tailNav']//*[@class='nav-item']//A[normalize-space(text())='" + name + "']");
    }

    /**
     * Find and click on a secondary link which has the text given.
     *
     * @param name - normalised string that you expect to see for this link. Case sensitive
     */
    public void followLink(String name) {
        myDriver.findElement(getNavlinkWithName(name)).click();
    }
}
