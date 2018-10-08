package by.epam.edu.webdriver.drivercreator;

import by.epam.edu.webdriver.data.DataProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aliaksandr_Sheliutsi on 1/25/2017.
 */
public class FirefoxRemoteDriverCreator extends WebDriverCreator {

    public FirefoxRemoteDriverCreator(String pathToDriver) {
        System.setProperty("webdriver.gecko.driver", pathToDriver);
    }

    public WebDriver factoryMethod() {
        URL url = null;
        try {
            url = new URL(DataProvider.getInstance().getProperty("NodeUrl"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            driver.close();
        }
        DesiredCapabilities cap = DesiredCapabilities.firefox();

        return new RemoteWebDriver(url, cap);
    }
}
