package testFramework.steps;

import io.cucumber.java.en.Given;
import org.junit.Assert;
import testFramework.helpers.HtmlReport;

public class Framework {

    @Given("I write to the html report {string}")
    public void iWriteToTheHtmlReport(String arg0) {
        //        System.out.println(arg0);
        HtmlReport.writeToHtmlReport(arg0);
    }

    @Given("I fail a test")
    public void iFailATest() {
        Assert.fail("A deliberately failing test");
    }

}
