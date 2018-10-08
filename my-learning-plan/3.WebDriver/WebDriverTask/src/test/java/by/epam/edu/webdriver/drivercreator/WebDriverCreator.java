package by.epam.edu.webdriver.drivercreator;

import org.openqa.selenium.WebDriver;

/**
 * Created by Aliaksandr_Sheliutsi on 1/25/2017.
 */
public abstract class WebDriverCreator {

    protected WebDriver driver;

    public abstract WebDriver factoryMethod();
}
