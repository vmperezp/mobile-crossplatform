package co.com.lab.certificacion.mobile.interactions;

import co.com.lab.certificacion.mobile.utils.DevicePlatform;
import co.com.lab.certificacion.mobile.utils.MobileDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

/**
 * Boton fisico atras. Solo existe en Android; los escenarios que lo usan estan
 * marcados con @AndroidOnly, y ademas se valida aqui para que la interaccion no
 * falle si alguien la reutiliza en iOS.
 */
public class PressBackKey implements Interaction {

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (DevicePlatform.isAndroid()) {
            MobileDriver.android().navigate().back();
        }
    }
}
