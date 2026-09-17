package co.com.lab.certificacion.mobile;

import co.com.lab.certificacion.mobile.utils.DevicePlatform;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Verificacion previa: valida la configuracion ANTES de gastar una sesion en la
 * granja. Corre en segundos y sin dispositivo.
 *
 *   ./gradlew test --tests "*PreflightTest"
 *   ./gradlew test --tests "*PreflightTest" -Dproperties=serenity-ios.properties
 */
public class PreflightTest {

    private static final String PROPERTIES_KEY = "properties";
    private static final String DEFAULT_PROPERTIES_FILE = "serenity.properties";

    private Properties activeConfiguration() throws IOException {
        Path file = Paths.get(System.getProperty(PROPERTIES_KEY, DEFAULT_PROPERTIES_FILE));
        assertTrue("No existe el archivo de configuracion: " + file, Files.exists(file));
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(file)) {
            properties.load(input);
        }
        return properties;
    }

    @Test
    public void laPlataformaActivaEsAndroidOIos() {
        String platform = DevicePlatform.name();
        System.out.println("Plataforma resuelta: " + platform);
        assertTrue("appium.platformName debe ser Android o iOS, y llego: " + platform,
                platform.startsWith("android") || platform.startsWith("ios"));
    }

    @Test
    public void lasCapabilitiesObligatoriasEstanPresentes() throws IOException {
        Properties configuration = activeConfiguration();
        for (String key : new String[]{"webdriver.driver", "appium.platformName", "appium.deviceName",
                "appium.automationName", "appium.app", "appium.hub"}) {
            assertNotNull("Falta la capability " + key, configuration.getProperty(key));
        }
    }

    @Test
    public void laAppFueReemplazadaPorUnIdRealDeLambdaTest() throws IOException {
        String app = activeConfiguration().getProperty("appium.app", "");
        assertFalse("appium.app todavia tiene el placeholder: sube la app y pon el lt://APP...",
                app.contains("APP_ID_"));
        assertTrue("appium.app deberia empezar por lt:// o ser una ruta local", app.startsWith("lt://") || app.contains("/"));
    }

    @Test
    public void elHubTieneCredenciales() throws IOException {
        String hub = System.getProperty("appium.hub", activeConfiguration().getProperty("appium.hub", ""));
        assertFalse("El hub sigue con el placeholder: exporta LT_USERNAME y LT_ACCESS_KEY y usa run-android.sh / run-ios.sh",
                hub.contains("TU_USUARIO") || hub.contains("TU_ACCESS_KEY"));
    }
}
