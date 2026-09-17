package co.com.lab.certificacion.mobile.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.DOCUMENT_TYPE_OPTION;
import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.SELECT_DOCUMENT_TYPE;

/**
 * Abre el dropdown y elige una opcion.
 *
 * En Flutter el menu del dropdown se abre en una ruta aparte, encima de la
 * pantalla, por eso hay que esperar a que la opcion aparezca despues del clic.
 */
public class SelectDocumentType implements Interaction {

    private static final int TIMEOUT = 15;

    private final String documentType;

    public SelectDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(SELECT_DOCUMENT_TYPE, WebElementStateMatchers.isVisible()).forNoMoreThan(TIMEOUT).seconds(),
                Click.on(SELECT_DOCUMENT_TYPE),
                WaitUntil.the(DOCUMENT_TYPE_OPTION.of(slug(documentType)), WebElementStateMatchers.isVisible())
                        .forNoMoreThan(TIMEOUT).seconds(),
                Click.on(DOCUMENT_TYPE_OPTION.of(slug(documentType)))
        );
    }

    /**
     * Misma conversion que hace la app: 'Cedula de ciudadania' -> 'cedula_de_ciudadania'.
     */
    private String slug(String value) {
        return value.toLowerCase().replace(" ", "_");
    }
}
