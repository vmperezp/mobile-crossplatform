package co.com.lab.certificacion.mobile.utils;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Fabrica de Targets multiplataforma.
 */
public final class PlatformTarget {

    private PlatformTarget() {
    }

    /**
     * Localiza por el Semantics(identifier:) de la app Flutter.
     *
     * Ese identificador cae como resource-id en Android y como
     * accessibilityIdentifier (atributo name) en iOS, asi que un mismo valor
     * sirve para las dos plataformas y lo unico que cambia es el atributo del
     * XPath.
     *
     * Se usa XPath en lugar de By.id porque asi el Target admite parametros
     * dinamicos con .of("valor"), como las opciones del dropdown.
     */
    public static Target byIdentifier(String name, String identifier) {
        return the(name,
                "//*[@resource-id='" + identifier + "']",
                "//*[@name='" + identifier + "']");
    }

    /**
     * Version con objetos By: id, accessibility id, predicate, uiautomator.
     */
    public static Target the(String name, By androidLocator, By iosLocator) {
        return Target.the(name).located(DevicePlatform.isAndroid() ? androidLocator : iosLocator);
    }

    /**
     * Version con XPath en texto, para los casos donde el arbol nativo difiere
     * de verdad entre plataformas. Es la unica que admite .of("valor").
     */
    public static Target the(String name, String androidXpath, String iosXpath) {
        return Target.the(name).locatedBy(DevicePlatform.isAndroid() ? androidXpath : iosXpath);
    }
}
