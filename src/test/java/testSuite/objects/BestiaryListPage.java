package testSuite.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testFramework.helpers.WebElements;

import java.util.List;

public class BestiaryListPage extends GreenlandsCommonPage {
    // Locators
    private final By beastListItem = By.xpath("//*[@id='creatureList']//*[contains(@class,'beastListItem')]");
    private final By itemHeading = By.xpath("//h2/a"); // find the text of the heading from the list item
    private final By itemText = By.tagName("p");
    private final By itemImageList = By.tagName("img");
    private final By itemCaptionList = By.tagName("figcaption");
    private final By itemVideoList = By.tagName("iframe");

    // Housekeeping
    public BestiaryListPage(WebDriver driver) { super(driver, By.xpath("//BODY[@id='bestiary']")); }

    private By specificLink(String text) {
        return By.xpath("//*[@id='creatureList']//*[contains(@class,'beastListItem')]//h2/a[normalize-space(text())='" + text + "']");
    }

    // Actions
    public List<WebElement> getBeastList() {
        return myDriver.findElements(beastListItem);
    }

    public String getItemHeader(WebElement listItem) {
        return listItem.findElement(itemHeading).getText();
    }

    public String getItemText(WebElement listItem) {
        return listItem.findElement(itemText).getText();
    }

    public List<WebElement> getItemImageList(WebElement listItem) {
        return listItem.findElements(itemImageList);
    }

    public List<WebElement> getItemCaptionList(WebElement listItem) {
        return listItem.findElements(itemCaptionList);
    }

    public String getTextOfCaption(WebElement element) {
        return element.getText();
    }

    public boolean itemHasVideo(WebElement listIem) {
        return WebElements.elementExists(listIem, itemVideoList);
    }

    public void followLinkWithText(String linkText) {
        myDriver.findElement(specificLink(linkText)).click();
    }

}
