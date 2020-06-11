package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GreenlandsCommonPage {
    @FindBy(tagName = "H1")
    public WebElement pageHeading;
    @FindBy(id = "contactLink")
    public WebElement contactLink;
    @FindBy(id = "membershipLink")
    public WebElement membershipLink;
    @FindBy(id = "policyLink")
    public WebElement policyLink;
    private WebDriver myDriver;

    public GreenlandsCommonPage(WebDriver driver) {
        myDriver = driver;
        PageFactory.initElements(myDriver, this);
    }

    public void goToBottomOfPage() {
        myDriver.findElement(By.tagName("BODY")).sendKeys(Keys.chord(Keys.CONTROL, Keys.DOWN));
    }
}
