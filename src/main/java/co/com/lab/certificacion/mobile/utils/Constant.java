package co.com.lab.certificacion.mobile.utils;

public enum Constant {

    // Llaves de las tablas de datos de los features
    TIPO_DOCUMENTO("document-type"),
    USUARIO("username"),
    CONTRASENA("password"),

    // Mensajes esperados de la app
    MENSAJE_DATOS_NO_COINCIDEN("Los datos no coinciden"),
    MENSAJE_USUARIO_BLOQUEADO("Tu usuario esta bloqueado");

    private final String valor;

    Constant(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
