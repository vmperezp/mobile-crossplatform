package co.com.lab.certificacion.mobile.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.INPUT_USER;

public class TheLoginFormIsVisible implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return INPUT_USER.resolveFor(actor).isCurrentlyVisible();
    }
}
