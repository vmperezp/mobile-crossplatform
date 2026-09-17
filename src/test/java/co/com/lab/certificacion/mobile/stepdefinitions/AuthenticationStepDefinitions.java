package co.com.lab.certificacion.mobile.stepdefinitions;

import co.com.lab.certificacion.mobile.tasks.factories.Ready;
import co.com.lab.certificacion.mobile.tasks.factories.Validate;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;

import java.util.Map;

import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.INPUT_USER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AuthenticationStepDefinitions {

    private static final String ACTOR = "Usuario";

    @Given("^the user is on the login screen$")
    public void theUserIsOnTheLoginScreen() {
        OnStage.theActorCalled(ACTOR).wasAbleTo(
                Ready.theApplication()
        );
    }

    @When("^the user logs in with the following credentials$")
    public void theUserLogsInWithTheFollowingCredentials(DataTable featureData) {
        Map<String, String> credentials = featureData.transpose().asMap(String.class, String.class);
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ready.authenticate(credentials)
        );
    }

    @Then("^the user should see the greeting \"([^\"]*)\"$")
    public void theUserShouldSeeTheGreeting(String expectedGreeting) {
        OnStage.theActorInTheSpotlight().should(
                GivenWhenThen.seeThat(Validate.theHomeGreeting(), equalTo(expectedGreeting))
        );
    }

    @Then("^the user should see the login error message \"([^\"]*)\"$")
    public void theUserShouldSeeTheLoginErrorMessage(String expectedMessage) {
        OnStage.theActorInTheSpotlight().should(
                GivenWhenThen.seeThat(Validate.theLoginErrorMessage(), equalTo(expectedMessage))
        );
    }

    @When("^the user taps the document number field$")
    public void theUserTapsTheDocumentNumberField() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Click.on(INPUT_USER)
        );
    }

    @And("^the user presses the device back button$")
    public void theUserPressesTheDeviceBackButton() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ready.pressBackKey()
        );
    }

    @Then("^the login form should still be visible$")
    public void theLoginFormShouldStillBeVisible() {
        OnStage.theActorInTheSpotlight().should(
                GivenWhenThen.seeThat(Validate.theLoginFormIsVisible(), is(true))
        );
    }
}
