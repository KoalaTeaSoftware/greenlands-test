package testFramework.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BrowserObject {
    // I can't imagine this being used ina ny other context, but you never know
    private WebDriver myDriver = Context.defaultDriver;

    /**
     * Wait until the browser thinks that it has this many tabs
     *
     * @param expectedCount
     */
    public void waitForTabCount(int expectedCount) {
        new WebDriverWait(
                myDriver,
                Duration.ofSeconds(Context.pageLoadWait))
                .until(ExpectedConditions.numberOfWindowsToBe(expectedCount));
    }

    /**
     * I have not seen it crash, but it probably with throw IndexOutOfBoundsException, if it can
     *
     * @param tabIndex - starts at zero
     */
    public void switchToTabIndexedBy(final int tabIndex) {
        List<String> browserTabs = new ArrayList<>(myDriver.getWindowHandles());
        Context.defaultDriver.switchTo().window(browserTabs.get(tabIndex));
    }

    /**
     * Closing the _current_ tab, not the window
     */
    public void closeCurrentTab() {
        myDriver.close();
    }
}
