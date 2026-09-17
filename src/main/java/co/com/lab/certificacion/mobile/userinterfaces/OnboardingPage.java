package co.com.lab.certificacion.mobile.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

import static co.com.lab.certificacion.mobile.utils.PlatformTarget.byIdentifier;

/**
 * Pantalla de onboarding, entre el splash y el login.
 */
public class OnboardingPage {

    public static final Target BTN_CONTINUE = byIdentifier("Boton Continuar", "onboarding_continue_button");

    private OnboardingPage() {
    }
}
