package co.com.lab.certificacion.mobile.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Resuelve la plataforma sobre la que se esta ejecutando la suite.
 *
 * Lee la capability appium.platformName del mismo archivo de properties que usa
 * Serenity (serenity.properties por defecto, o el indicado con -Dproperties=...).
 * Asi la plataforma se define en un unico lugar -el archivo de configuracion-
 * y no se duplica dentro del codigo.
 *
 * Una system property (-Dappium.platformName=iOS) tiene prioridad, igual que en
 * Serenity.
 */
public final class DevicePlatform {

    private static final String ANDROID = "android";
    private static final String PLATFORM_KEY = "appium.platformName";
    private static final String PROPERTIES_KEY = "properties";
    private static final String DEFAULT_PROPERTIES_FILE = "serenity.properties";

    private static final Properties CONFIGURATION = loadConfiguration();

    private DevicePlatform() {
    }

    private static Properties loadConfiguration() {
        Properties properties = new Properties();
        Path configurationFile = Paths.get(System.getProperty(PROPERTIES_KEY, DEFAULT_PROPERTIES_FILE));
        if (Files.exists(configurationFile)) {
            try (InputStream input = Files.newInputStream(configurationFile)) {
                properties.load(input);
            } catch (IOException e) {
                throw new IllegalStateException("No fue posible leer el archivo de configuracion: " + configurationFile, e);
            }
        }
        return properties;
    }

    public static String name() {
        String fromCommandLine = System.getProperty(PLATFORM_KEY);
        String platform = (fromCommandLine == null || fromCommandLine.trim().isEmpty())
                ? CONFIGURATION.getProperty(PLATFORM_KEY, ANDROID)
                : fromCommandLine;
        return platform.trim().toLowerCase();
    }

    public static boolean isAndroid() {
        return name().startsWith(ANDROID);
    }

    public static boolean isIos() {
        return !isAndroid();
    }
}
