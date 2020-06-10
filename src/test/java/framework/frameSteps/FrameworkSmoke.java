package framework.frameSteps;

import framework.helpers.HtmlReport;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;

/**
 * simple little steps for smoke testing the framework
 */
public class FrameworkSmoke {

    @Given("I say on Stdout {string}")
    public void iSayOnStdout(String arg0) {
        System.out.println(">" + arg0 + "<");
    }

    @And("the step fails")
    public void theStepFails() {
        Assert.fail("This is a dummy step to smoke test the framework");
    }

    @And("I say in the scenario report {string}")
    public void iSayInTheScenarioReport(String message) {
        HtmlReport.writeToHtmlReport(message);
    }

}
