package testFramework.helpers;

import testFramework.Context;

public class HtmlReport {
    /**
     * @param label - what you want to see written in the report
     */
    public static void writeScreenShotToHtmlReport(String label) {
        try {
            Context.scenario.write(label);
        } catch (Exception e) {
            writeToHtmlReport("Unable to capture the screenshot: " + e.getMessage());
        }
    }

    /**
     * Just what is says on the tin.
     *
     * @param message - what you ant to see in the report
     */
    public static void writeToHtmlReport(String message) {
        Context.scenario.write(message);
    }
}
