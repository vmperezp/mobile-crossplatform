package co.com.lab.certificacion.mobile.interactions;

import co.com.lab.certificacion.mobile.utils.MobileDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.Dimension;


public class ScrollUntilVisible implements Interaction {

    private static final int MAX_SWIPES = 6;

    private final Target target;

    public ScrollUntilVisible(Target target) {
        this.target = target;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T extends Actor> void performAs(T actor) {
        AppiumDriver<MobileElement> driver = MobileDriver.current();
        Dimension screen = driver.manage().window().getSize();

        int x = screen.getWidth() / 2;
        int startY = (int) (screen.getHeight() * 0.75);
        int endY = (int) (screen.getHeight() * 0.30);

        for (int attempt = 0; attempt < MAX_SWIPES; attempt++) {
            if (isAlreadyVisible(actor)) {
                return;
            }
            new TouchAction(driver)
                    .press(PointOption.point(x, startY))
                    .moveTo(PointOption.point(x, endY))
                    .release()
                    .perform();
        }
    }


    private <T extends Actor> boolean isAlreadyVisible(T actor) {
        try {
            return target.resolveFor(actor).isCurrentlyVisible();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
