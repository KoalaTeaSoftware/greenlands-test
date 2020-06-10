package framework;

import framework.helpers.HtmlReport;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void beforeScenario(Scenario givenScenario) {
        ContextOfScenario.scenario = givenScenario;
    }

    @After
    public void afterScenario(Scenario scenario) {
        // this may seem a bit involved, but a direct use of getStatus().equals does not yield the hoped-for result
        if (!scenario.getStatus().name().equalsIgnoreCase("passed")) {
            HtmlReport.writeScreenShotToHtmlReport(
                    scenario,
                    "Screenshot taken because this scenario is marked as " + scenario.getStatus().toString());
        }
        ContextOfScenario.actor.closeDriver();
    }
}
