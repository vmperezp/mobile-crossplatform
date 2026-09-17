package co.com.lab.certificacion.mobile.tasks.factories;

import co.com.lab.certificacion.mobile.interactions.Login;
import co.com.lab.certificacion.mobile.interactions.PressBackKey;
import co.com.lab.certificacion.mobile.interactions.ScrollUntilVisible;
import co.com.lab.certificacion.mobile.interactions.SelectDocumentType;
import co.com.lab.certificacion.mobile.interactions.TypeText;
import co.com.lab.certificacion.mobile.tasks.Authenticate;
import co.com.lab.certificacion.mobile.tasks.OpenTheApplication;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.targets.Target;

import java.util.Map;

/**
 * Fabrica de tasks e interactions, misma convencion del proyecto AppNegocios.
 */
public class Ready {

    private Ready() {
    }

    public static OpenTheApplication theApplication() {
        return Tasks.instrumented(OpenTheApplication.class);
    }

    public static Authenticate authenticate(Map<String, String> credentials) {
        return Tasks.instrumented(Authenticate.class, credentials);
    }

    public static Login login(Map<String, String> credentials) {
        return Tasks.instrumented(Login.class, credentials);
    }

    public static SelectDocumentType selectDocumentType(String documentType) {
        return Tasks.instrumented(SelectDocumentType.class, documentType);
    }

    public static TypeText typeText(String text, Target field) {
        return Tasks.instrumented(TypeText.class, text, field);
    }

    public static ScrollUntilVisible scrollUntilVisible(Target target) {
        return Tasks.instrumented(ScrollUntilVisible.class, target);
    }

    public static PressBackKey pressBackKey() {
        return Tasks.instrumented(PressBackKey.class);
    }
}
