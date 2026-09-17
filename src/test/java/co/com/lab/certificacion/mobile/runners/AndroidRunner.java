package co.com.lab.certificacion.mobile.runners;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Ejecuta los escenarios multiplataforma mas los exclusivos de Android.
 * La coma entre tags equivale a un OR en Cucumber.
 *
 * ./gradlew clean test aggregate --tests "*AndroidRunner"
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "co.com.lab.certificacion.mobile.stepdefinitions",
        tags = {"@CrossPlatform,@AndroidOnly"}
)
public class AndroidRunner {
}
