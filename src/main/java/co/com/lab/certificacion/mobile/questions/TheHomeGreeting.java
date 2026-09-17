package co.com.lab.certificacion.mobile.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.lab.certificacion.mobile.userinterfaces.HomePage.GREETING;

public class TheHomeGreeting implements Question<String> {

    private static final int TIMEOUT = 30;

    @Override
    public String answeredBy(Actor actor) {
        actor.attemptsTo(
                WaitUntil.the(GREETING, WebElementStateMatchers.isVisible()).forNoMoreThan(TIMEOUT).seconds()
        );
        return GREETING.resolveFor(actor).getText().trim();
    }
}
