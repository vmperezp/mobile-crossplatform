package co.com.lab.certificacion.mobile.interactions;

import co.com.lab.certificacion.mobile.tasks.factories.Ready;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.Map;

import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.BTN_SUBMIT;
import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.INPUT_PASSWORD;
import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.INPUT_USER;
import static co.com.lab.certificacion.mobile.userinterfaces.LoginPage.LOADER;
import static co.com.lab.certificacion.mobile.utils.Constant.CONTRASENA;
import static co.com.lab.certificacion.mobile.utils.Constant.TIPO_DOCUMENTO;
import static co.com.lab.certificacion.mobile.utils.Constant.USUARIO;

/**
 * Diligencia el formulario de autenticacion, lo envia y espera a que el
 * servicio responda.
 *
 * El servicio simulado demora 3 segundos: por eso se espera a que el loader
 * desaparezca en vez de asumir que la navegacion es inmediata.
 */
public class Login implements Interaction {

    private static final int TIMEOUT = 20;
    private static final int SERVICE_TIMEOUT = 30;

    private final Map<String, String> credentials;

    public Login(Map<String, String> credentials) {
        this.credentials = credentials;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(INPUT_USER, WebElementStateMatchers.isVisible()).forNoMoreThan(TIMEOUT).seconds(),
                Ready.selectDocumentType(credentials.get(TIPO_DOCUMENTO.getValor())),
                Ready.typeText(credentials.get(USUARIO.getValor()), INPUT_USER),
                Ready.typeText(credentials.get(CONTRASENA.getValor()), INPUT_PASSWORD),

                WaitUntil.the(BTN_SUBMIT, WebElementStateMatchers.isVisible()).forNoMoreThan(TIMEOUT).seconds(),
                Click.on(BTN_SUBMIT),

                WaitUntil.the(LOADER, WebElementStateMatchers.isNotCurrentlyVisible()).forNoMoreThan(SERVICE_TIMEOUT).seconds()
        );
    }
}
