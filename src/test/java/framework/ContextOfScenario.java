package framework;

import framework.actors.Actor;
import io.cucumber.java.Scenario;

/**
 * Use this class to share data between steps in a scenario
 * When the scenario starts
 */
public class ContextOfScenario {
    public static Scenario scenario;
    public static Actor actor;
}