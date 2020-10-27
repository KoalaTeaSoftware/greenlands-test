package testSuite.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import testFramework.Context;
import testSuite.objects.BestiaryDetailPage;
import testSuite.objects.BestiaryListPage;

import java.util.List;
import java.util.Map;

public class BestiarySteps {
    @Then("there are more than {int} beasts listed")
    public void thereAreMoreThanBeastsListed(int minNumber) {
        BestiaryListPage myPage = new BestiaryListPage(Context.defaultDriver);
        List<WebElement> list = myPage.getBeastList();

        Assert.assertTrue(
                String.format("The should be at least %d beasts listed in the bestiary, there are only %d",
                        minNumber,
                        list.size()),
                list.size() >= minNumber);
    }

    @Then("all of the beast entries are well formed")
    public void allOfTheBeastEntriesAreWellFormed(DataTable dataTable) {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        int minHeaderSize = 0;
        int minTextSize = 0;
        int minCaptionLength = 0;
        int minNumberImages = 0;
        int maxNumberImages = 0;

        try {
            minHeaderSize = Integer.parseInt(dataMap.get("minHeaderSize"));
            minTextSize = Integer.parseInt(dataMap.get("minTextSize"));
            minCaptionLength = Integer.parseInt(dataMap.get("minCaptionLength"));
            minNumberImages = Integer.parseInt(dataMap.get("minNumberImages"));
            maxNumberImages = Integer.parseInt(dataMap.get("maxNumberImages"));
        } catch (NumberFormatException e) {
            Assert.fail("Unable to understand parameters: " + e.getMessage());
        }

        BestiaryListPage myPage = new BestiaryListPage(Context.defaultDriver);
        List<WebElement> list = myPage.getBeastList();

        SoftAssert sa = new SoftAssert();
        int beastIdx = 0;

        for (WebElement listItem : list) {
            beastIdx++;
            sa.assertTrue(
                    myPage.getItemHeader(listItem).length() >= minHeaderSize,
                    String.format(
                            "For beast %d, the heading contains only %d letters. It should contain at least %d",
                            beastIdx, myPage.getItemHeader(listItem).length(), minHeaderSize)
            );

            sa.assertTrue(
                    myPage.getItemText(listItem).length() >= minTextSize,
                    String.format(
                            "For beast %d, the description contains only %d letters. It should contain at least %d",
                            beastIdx, myPage.getItemHeader(listItem).length(), minTextSize)
            );

            final List<WebElement> listOfImages = myPage.getItemImageList(listItem);
            final List<WebElement> listOfCaptions = myPage.getItemCaptionList(listItem);

            sa.assertEquals(listOfImages.size(), listOfCaptions.size(),
                    String.format(
                            "For beast %d, the list of images and list of captions differ in length (%d, %d respectively)",
                            beastIdx, listOfImages.size(), listOfCaptions.size())
            );

            sa.assertTrue(listOfImages.size() >= minNumberImages,
                    String.format(
                            "For beast %d, the list of images is %d long, it should be at least %d long",
                            beastIdx, listOfImages.size(), minNumberImages)
            );

            sa.assertTrue(listOfImages.size() <= maxNumberImages,
                    String.format(
                            "For beast %d, the list of images is %d long, it should be at no more than %d long",
                            beastIdx, listOfImages.size(), maxNumberImages)
            );

            if (myPage.itemHasVideo(listItem)) {
                sa.assertTrue(listOfImages.size() == 1,
                        String.format(
                                "For beast %d, if there is a video, then there should be only 1 image",
                                beastIdx)
                );
            }

            int imgIdx = 0;
            for (WebElement img : listOfImages) {
                imgIdx++;
                sa.assertTrue(myPage.browserShowsImage(img),
                        String.format("For beast %d, image number %d, does not appear to be fully displayed",
                                beastIdx, imgIdx)
                );
            }

            imgIdx = 0;
            for (WebElement caption : listOfCaptions) {
                imgIdx++;
                sa.assertTrue(myPage.getTextOfCaption(caption).length() > minCaptionLength,
                        String.format(
                                "For beast %d, image number %d, the caption appears to be too small (%d chars long)",
                                beastIdx, imgIdx, myPage.getTextOfCaption(caption).length())
                );
            }
        }

        sa.assertAll();
    }

    @When("I follow a details link for item entitles {string}")
    public void iFollowADetailsLinkForItemEntitles(String linkText) {
        BestiaryListPage myPage = new BestiaryListPage(Context.defaultDriver);
        myPage.followLinkWithText(linkText);
        // force the wait for the new page to happen
        @SuppressWarnings("unused")
        BestiaryDetailPage bestiaryDetailPage = new BestiaryDetailPage(Context.defaultDriver);
    }
}
