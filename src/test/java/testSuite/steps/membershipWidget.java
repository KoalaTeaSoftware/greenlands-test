package testSuite.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import testSuite.objects.MembershipWidget;

public class membershipWidget {
    @Then("the membership widget is visible")
    public void theMembershipWidgetIsVisible() {
        MembershipWidget membershipWidget = new MembershipWidget();
        Assert.assertTrue("The membership should be visible", membershipWidget.widgetIsVisible());
    }

    @And("the membership widget is not visible")
    public void theMembershipWidgetIsNotVisible() {
        MembershipWidget membershipWidget = new MembershipWidget();
        Assert.assertTrue("The membership should be visible", membershipWidget.widgetIsNotVisible());
    }
}
