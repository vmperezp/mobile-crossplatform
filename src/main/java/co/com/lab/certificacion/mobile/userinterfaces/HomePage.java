package co.com.lab.certificacion.mobile.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

import static co.com.lab.certificacion.mobile.utils.PlatformTarget.byIdentifier;

/**
 * Pantalla posterior a una autenticacion exitosa.
 */
public class HomePage {

    public static final Target GREETING = byIdentifier("Saludo del home", "home_greeting");

    public static final Target ACCOUNT_TILE = byIdentifier("Tarjeta de la cuenta", "home_account_tile");

    private HomePage() {
    }
}
