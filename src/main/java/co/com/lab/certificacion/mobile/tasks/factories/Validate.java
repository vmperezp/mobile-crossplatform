package co.com.lab.certificacion.mobile.tasks.factories;

import co.com.lab.certificacion.mobile.questions.TheHomeGreeting;
import co.com.lab.certificacion.mobile.questions.TheLoginFormIsVisible;
import co.com.lab.certificacion.mobile.questions.TheLoginErrorMessage;

public class Validate {

    private Validate() {
    }

    public static TheHomeGreeting theHomeGreeting() {
        return new TheHomeGreeting();
    }

    public static TheLoginErrorMessage theLoginErrorMessage() {
        return new TheLoginErrorMessage();
    }

    public static TheLoginFormIsVisible theLoginFormIsVisible() {
        return new TheLoginFormIsVisible();
    }
}
