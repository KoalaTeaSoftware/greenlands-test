package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePageObject extends GreenlandsCommonPage {

    private WebDriver myDriver;

    public HomePageObject(WebDriver driver) {
        super(driver);
        myDriver = driver;
//        PageFactory.initElements(driver, this.getClass());
    }

    public WebElement getLinkForChapter(String chapterName) {
        return myDriver.findElement(By.xpath("//a[@title='" + chapterName + "']"));
    }

    /**
     * Note: this is case insensitive
     *
     * @param chapterName
     * @return
     */
    public WebElement getImgForChapter(String chapterName) {
        return myDriver.findElement(By.xpath("//a[@id='" + chapterName.toLowerCase() + "Link']//img"));
    }
}
