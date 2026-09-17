package co.com.lab.certificacion.mobile.interactions;

import co.com.lab.certificacion.mobile.utils.MobileDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

/**
 * Escribe un texto en un campo y cierra el teclado.
 *
 * Se encapsula porque el cierre del teclado es la parte que se comporta
 * distinto en Android y en iOS; el resto de la suite no tiene que saberlo.
 */
public class TypeText implements Interaction {

    private static final int TIMEOUT = 15;

    private final String text;
    private final Target field;

    public TypeText(String text, Target field) {
        this.text = text;
        this.field = field;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(field, WebElementStateMatchers.isClickable()).forNoMoreThan(TIMEOUT).seconds(),
                Click.on(field),
                Enter.theValue(text).into(field)
        );
        MobileDriver.hideKeyboardIfPresent();
    }
}
