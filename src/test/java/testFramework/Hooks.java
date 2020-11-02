package testFramework;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static testFramework.helpers.Reports.writePageSourceToHtmlReport;
import static testFramework.helpers.Reports.writeScreenShotToHtmlReport;

public class Hooks {
    @Before
    public void beforeScenario(Scenario givenScenario) {
        System.out.println("[info] Refreshing the context for the new scenario");
        Context.scenario = givenScenario;
        Context.defaultDriver = Context.defaultActor.getDriver();
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("[info] Scenario cleanup hook activated");
        // this may seem a bit involved, but a direct use of getStatus().equals does not yield the hoped-for result
        if (scenario.isFailed()) {
            writeScreenShotToHtmlReport("Screenshot taken because this scenario is marked as " + scenario.getStatus().toString());
            writePageSourceToHtmlReport();
        }
        Context.defaultActor.closeDriver();
    }

}
