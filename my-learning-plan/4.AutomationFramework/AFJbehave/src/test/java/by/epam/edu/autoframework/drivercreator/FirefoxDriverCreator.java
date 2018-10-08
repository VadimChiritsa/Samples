package by.epam.edu.autoframework.drivercreator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Aliaksandr_Sheliutsi on 2/1/2017.
 */
public class FirefoxDriverCreator extends WebDriverCreator {

    public FirefoxDriverCreator(String pathToDriver) {
        System.setProperty("webdriver.gecko.driver", pathToDriver);
    }
    @Override
    public WebDriver factoryMethod() {
        return new FirefoxDriver();
    }
}
