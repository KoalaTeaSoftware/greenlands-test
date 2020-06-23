package objects;

import framework.objects.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GreenlandsCommonPage extends HtmlPage {
    @FindBy(tagName = "H1")
    public WebElement pageHeading;
    @FindBy(id = "contactLink")
    public WebElement contactLink;
    @FindBy(id = "membershipLink")
    public WebElement membershipLink;
    @FindBy(id = "policyLink")
    public WebElement policyLink;
    @FindBy(id = "signUp")
    public WebElement membershipModal;
    @FindBy(tagName = "h1")
    public WebElement headerTag;

    private WebDriver myDriver;

    public GreenlandsCommonPage(WebDriver driver) {
        super(driver);
        myDriver = driver;

//        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
//                .withTimeout(Duration.ofSeconds(30))
//                .pollingEvery(Duration.ofMillis(200))
//                .ignoring(NoSuchElementException.class);

        // the sign up modal is at the bottom of all of the pages, its presence strongly implies that the page has been completely drawn
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        membershipModal = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("signUp")));

        PageFactory.initElements(myDriver, this);
    }

    public void goToBottomOfPage() {
        myDriver.findElement(By.tagName("BODY")).sendKeys(Keys.chord(Keys.CONTROL, Keys.DOWN));
    }
}
