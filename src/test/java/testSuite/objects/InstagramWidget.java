package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testFramework.objects.WebPageObj;

import java.util.List;

public class InstagramWidget extends WebPageObj {
    private final By sectionLocator = By.id("instagram");
    private final By imageLocator = By.className("imgEric");
    private final By followMeButtonLocator = By.xpath("//*[@id='instaButtonRow']//A");

    public InstagramWidget(WebDriver defaultDriver) {
        super(defaultDriver);
    }

    public List<WebElement> getImageList() {
        return myDriver.findElement(sectionLocator).findElements(imageLocator);
    }

    public void followMe() {
        myDriver.findElement(followMeButtonLocator).click();
    }
}
