package co.com.lab.certificacion.mobile.runners;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Ejecuta los escenarios multiplataforma mas los exclusivos de iOS.
 *
 * ./gradlew clean test aggregate -Dproperties=serenity-ios.properties --tests "*IosRunner"
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "co.com.lab.certificacion.mobile.stepdefinitions",
        tags = {"@CrossPlatform,@IosOnly"}
)
public class IosRunner {
}
