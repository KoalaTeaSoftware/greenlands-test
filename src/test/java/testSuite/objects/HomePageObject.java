package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePageObject extends GreenlandsCommonPage {
    // housekeeping
    private final WebDriver myDriver;

    // actions

    /**
     * Note the use of normalize() - It is most likely that the user's view of the link text is a normalised string,
     * but the IDE may have munged it (to no end effect - except for space chars sent)
     *
     * @param displayedText - case sensitive the text that the user sees
     * @return - the first element in the main nav bar that IS the text given in the param
     */
    public WebElement getLinkForChapter(String displayedText) {
        // byLinkText consistently fails to find the these links, but the following is fast enough,and reliable
        return myDriver.findElement(chapterLink(displayedText));
    }

    /**
     * Note: this is case insensitive
     *
     * @param chapterName - the string that you see on the page leading/trailing spaces may cause problems
     * @return - the img tag that goes with that chapter name
     */
    public WebElement getImgForChapter(String chapterName) {
        return myDriver.findElement(By.xpath("//a[@id='" + chapterName.toLowerCase() + "Link']//img"));
    }

    public HomePageObject(WebDriver driver) {
        super(driver);
        myDriver = driver;
    }

    // locations
    private final By chapterLink(String name) {
        return By.xpath("//*[@id='page-content']//a//h2[normalize-space(text())='" + name + "']");
    }

}
