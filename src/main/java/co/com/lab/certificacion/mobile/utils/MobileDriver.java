package co.com.lab.certificacion.mobile.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriverException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Acceso puntual al driver nativo para lo que Screenplay no cubre.
 */
public final class MobileDriver {

    private MobileDriver() {
    }

    @SuppressWarnings("unchecked")
    public static AppiumDriver<MobileElement> current() {
        return (AppiumDriver<MobileElement>) ThucydidesWebDriverSupport.getProxiedDriver();
    }

    @SuppressWarnings("unchecked")
    public static AndroidDriver<MobileElement> android() {
        return (AndroidDriver<MobileElement>) ThucydidesWebDriverSupport.getProxiedDriver();
    }

    /**
     * Cierra el teclado.
     *
     * Importa mas de lo que parece: con el teclado abierto, iOS reduce el area
     * visible del ScrollView y XCUITest deja de exponer en el arbol los
     * elementos que quedan por debajo. El boton de enviar desaparece aunque se
     * siga viendo en el video de la sesion.
     *
     * driver.hideKeyboard() no es fiable en XCUITest, asi que primero se usa el
     * comando propio del driver, que pulsa una tecla de cierre del teclado.
     */
    public static void hideKeyboardIfPresent() {
        AppiumDriver<MobileElement> driver = current();

        if (DevicePlatform.isIos()) {
            Map<String, Object> params = new HashMap<>();
            params.put("keys", Arrays.asList("Done", "Listo", "return", "Return"));
            try {
                driver.executeScript("mobile: hideKeyboard", params);
                return;
            } catch (WebDriverException e) {
                // Se intenta la via generica de abajo.
            }
        }

        try {
            driver.hideKeyboard();
        } catch (WebDriverException e) {
            // El teclado ya estaba oculto: no es un fallo de la prueba.
        }
    }
}
