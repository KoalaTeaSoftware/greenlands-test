Sample Cucumber / Selenium / Java UI Test Framework
====================================================

This framework is a work in progress. It will:

* Read config files
    * One for the SUT (.e.g its root URL) 
    * One for the test framework (e.g. which browser to use) 
* Run the Chrome and FFx on the local device (PC) (IE is a bit sad)
* Produce a pretty HTML report at the end

The framework comprises the following parts:
* ContextOfTest - items that remain constant for the entire run e.g.
    * Browser, headless or not, wait times, location of SUT
    * It is not likely that you will want alter stuff here, but you may want to read stuff from it
* ContextOfScenario - items that exist for the duration of each scenario
    * Create fields here to simply share data between steps within a scenario.
* Helpers - what you might expect
* Objects that may be useful to you
    * HtmlPage - simple thing that lets you see things like the title, you may want to extend this for your own page objects
    * JsonAPIResponse - similar shim that brings a few basics to the fore 

# Installation
* Goes into a Maven project
* The main parts of the project are 
    * src/test/java/framework
    * The pom.xml
* Your IDE will want to retrieve dependencies.

# Configuration
 
Edit the configuration files

* Framework properties  e.g.
    * Browser, 
    * Headless or not, 
    * Wait times, and so on ...
* SUT properties e.g.
    * URL for SUT
* HTML reports
    * The reports will be written where you specify in the plugin in the TestRunner, not in this file
    * Change the report title with this file

# Use
* The framework contains a smoke-test-the-framework capability. Use this to see that it is all correctly operational.
* Put your feature files in src/test/features
* Put your own test steps and other stuff under src/test/java
* Edit the test context config file to use different browsers for some simple x-browser testing, and so on.

# Roadmap
In no particular order

* Add RESTful API testing (transparent to the features)
* Add Safari to the browser factory
* Add more exciting 'options' to the browsers / configuration files
* Appium
* FTP report to somewhere (e.g. a web server)
