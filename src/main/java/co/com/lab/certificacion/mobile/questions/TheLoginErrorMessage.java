package co.com.lab.certificacion.mobile.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.ERROR_MESSAGE;

public class TheLoginErrorMessage implements Question<String> {

    private static final int TIMEOUT = 20;

    @Override
    public String answeredBy(Actor actor) {
        actor.attemptsTo(
                WaitUntil.the(ERROR_MESSAGE, WebElementStateMatchers.isVisible()).forNoMoreThan(TIMEOUT).seconds()
        );
        return ERROR_MESSAGE.resolveFor(actor).getText().trim();
    }
}
