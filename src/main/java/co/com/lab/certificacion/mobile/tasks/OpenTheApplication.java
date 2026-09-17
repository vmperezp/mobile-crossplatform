package co.com.lab.certificacion.mobile.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.INPUT_USER;
import static co.com.lab.certificacion.mobile.userinterfaces.OnboardingPage.BTN_CONTINUE;

/**
 * Lleva la app desde el arranque hasta la pantalla de autenticacion.
 *
 * Hay un splash de 2 segundos y un onboarding intermedio.
 *
 * Se espera por isVisible y no por isClickable: Flutter cuelga el
 * Semantics(identifier:) en un nodo contenedor (XCUIElementTypeOther en iOS),
 * y un contenedor nunca reporta clickable aunque el toque si llegue al boton.
 */
public class OpenTheApplication implements Task {

    private static final int SPLASH_TIMEOUT = 40;
    private static final int TIMEOUT = 20;

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(BTN_CONTINUE, WebElementStateMatchers.isVisible()).forNoMoreThan(SPLASH_TIMEOUT).seconds(),
                Click.on(BTN_CONTINUE),
                WaitUntil.the(INPUT_USER, WebElementStateMatchers.isVisible()).forNoMoreThan(TIMEOUT).seconds()
        );
    }
}
