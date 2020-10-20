package testFramework.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.Context;

import java.time.Duration;

public class AccessibilityChecker {
    /**
     * It is best to aim this directly at the single files that you create.
     * For example, Bootstrap's css invokes error messages (false negatives?) from this tester.
     *
     * @param urlOfCssFile - make it a single file.Scheme is not necessary
     */
    public AccessibilityChecker(String urlOfCssFile) {
        //https://wave.webaim.org/report#/https://rosegoldthorp.com/
        /*
        <div id="numbers">
					<ul>
						<li id="error"><img src="img/error.svg" alt=""> <span>2</span><br>Errors</li>
						<li id="contrast"><img src="img/contrast.svg" alt=""> <span>0</span><br>Contrast Errors</li>
						<li id="alert"><img src="img/alert.svg" alt=""> <span>2</span><br>Alerts</li>
						<li id="feature"><img src="img/feature.svg" alt=""> <span>6</span><br>Features</li>
						<li id="structure"><img src="img/structure.svg" alt=""> <span>3</span><br>Structural Elements</li>
						<li id="aria"><img src="img/aria.svg" alt=""> <span>4</span><br>ARIA</li>
					</ul>
				</div>
         */
        String fullUrl = "https://validator.w3.org/checklink?uri=";
        fullUrl += urlOfCssFile;
        fullUrl += "%2F&summary=on&hide_type=all&depth=&check=Check";

        Context.defaultActor.getResource(fullUrl);

        new WebDriverWait(Context.defaultDriver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.tagName("H3")));
    }

    /**
     * @return - whether it contains text that indicates success, or failure
     */
    public Boolean fileValidates() {
        if (Context.defaultDriver.findElement(By.tagName("h3")).getText().toLowerCase().contains("broken links"))
            return false; // it definitely says there is a problem

        // otherwise, hunt for the p that specifically indicates success
        for (WebElement p : Context.defaultDriver.findElements(By.tagName("p"))) {
            if (p.getText().equalsIgnoreCase("Valid links!"))
                return true;
        }
        return false;
    }

}