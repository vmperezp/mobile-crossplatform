package co.com.lab.certificacion.mobile.stepdefinitions;

import co.com.lab.certificacion.mobile.utils.MobileDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.openqa.selenium.JavascriptExecutor;

public class Hooks {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    /**
     * Reporta el resultado del escenario a LambdaTest para que el dashboard
     * muestre passed/failed en lugar de "completed".
     */
    @After
    public void reportStatusToLambdaTest(Scenario scenario) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) MobileDriver.current();
            executor.executeScript("lambda-status=" + (scenario.isFailed() ? "failed" : "passed"));
        } catch (RuntimeException e) {
            // La sesion pudo cerrarse antes; no debe afectar el resultado del escenario.
        }
    }
}
