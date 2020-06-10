package framework.frameSmokeRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        // a file path to the root of all features
        // this runner is specifically for the framework smoke test, so point it directly there
        features = "src/test/java/framework/frameSmokeFeatures",
        // a list of _package_ names, therefore names are not directly related to file paths
        glue = {
                // the @Before and @After will not be run unless the package containing them is listed here
                // Also, You will be wanting to use this package in your own test runner
                // it contains things like using a browser to visit a url
                "framework"
//                ,"your.frameSteps"
        },
        plugin = {
                "pretty",
                "html:/target/stdReports",
                "json:/target/cucumber.json",
                // see https://gitlab.com/monochromata-de/cucumber-reporting-plugin
                "de.monochromata.cucumber.report.PrettyReports:target/"
        }
        , tags = "@framework"
//        ,dryRun = true
)

public class FrameSmokeRunner {
}