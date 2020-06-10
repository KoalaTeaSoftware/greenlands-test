package framework.helpers;

import framework.ContextOfScenario;
import io.cucumber.java.Scenario;

public class HtmlReport {
    /**
     * Just what is says on the tin.
     *
     * @param message - what you ant to see in the report
     */
    public static void writeToHtmlReport(String message) {
        ContextOfScenario.scenario.write(message);
    }

    /**
     * As the different types of actor (browsers, of API) have different ways of
     * getting this info, we need to direct control to the right actor
     *
     * @param scenario - found in the scenario context
     * @param label    - what you want to see written in the report
     */
    public static void writeScreenShotToHtmlReport(Scenario scenario, String label) {
        try {
            ContextOfScenario.actor.embedScreenShot(scenario, label);
        } catch (Exception e) {
            // the actor could be null because the actor has not yet been defined
            // ToDo: this is not actually an error deserving of a full stack trace
            e.printStackTrace();
            writeToHtmlReport("Unable to capture the screenshot because the type of actor is unknown");
        }
    }

}
