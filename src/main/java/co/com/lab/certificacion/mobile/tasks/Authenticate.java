package co.com.lab.certificacion.mobile.tasks;

import co.com.lab.certificacion.mobile.tasks.factories.Ready;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.util.Map;

/**
 * Tarea de negocio: autenticarse en la aplicacion.
 *
 * Recibe las credenciales desde la tabla de datos del feature, igual que en el
 * proyecto AppNegocios.
 */
public class Authenticate implements Task {

    private final Map<String, String> credentials;

    public Authenticate(Map<String, String> credentials) {
        this.credentials = credentials;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Ready.login(credentials)
        );
    }
}
