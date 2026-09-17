package co.com.lab.certificacion.mobile.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

import static co.com.lab.certificacion.mobile.utils.PlatformTarget.byIdentifier;

public class LoginPage {

    public static final Target SELECT_DOCUMENT_TYPE = byIdentifier("Tipo de documento", "login_document_type");


    public static final Target DOCUMENT_TYPE_OPTION = byIdentifier("Opcion de tipo de documento", "login_document_option_{0}");

    public static final Target INPUT_USER = byIdentifier("Numero de documento", "login_user_field");

    public static final Target INPUT_PASSWORD = byIdentifier("Clave", "login_password_field");

    public static final Target BTN_SUBMIT = byIdentifier("Boton Ingresar", "login_submit_button");

    public static final Target LOADER = byIdentifier("Indicador de carga", "login_loader");

    public static final Target ERROR_MESSAGE = byIdentifier("Mensaje de error del login", "login_error_message");

    private LoginPage() {
    }
}
