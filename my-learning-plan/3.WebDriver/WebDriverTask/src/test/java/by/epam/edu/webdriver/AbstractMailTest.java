package by.epam.edu.webdriver;


import by.epam.edu.webdriver.data.DataProvider;
import by.epam.edu.webdriver.drivercreator.ChromeRemoteDriverCreator;
import by.epam.edu.webdriver.drivercreator.FirefoxRemoteDriverCreator;
import by.epam.edu.webdriver.drivercreator.WebDriverCreator;
import by.epam.edu.webdriver.framework.LoginPage;
import by.epam.edu.webdriver.framework.mail.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

/**
 * Created by Aliaksandr_Sheliutsi on 1/19/2017.
 */
public abstract class AbstractMailTest {
    String login = "";
    String passwd = "";
    String startUrl = "";

    protected WebDriver driver;

    protected LoginPage loginPage;
    protected MainPage mainPage;

    @BeforeTest()
    @Parameters("browser")
    public void loadState(String browser) {
        WebDriverCreator creator;

        if (browser.equals("firefox")) {
            creator = new FirefoxRemoteDriverCreator(DataProvider.getInstance().getProperty("PathToFirefoxDriver"));
        } else if (browser.equals("chrome")) {
            creator = new ChromeRemoteDriverCreator(DataProvider.getInstance().getProperty("PathToChromeDriver"));
        } else {//Just in case of a typo
            creator = new ChromeRemoteDriverCreator(DataProvider.getInstance().getProperty("PathToChromeDriver"));
        }
        driver = creator.factoryMethod();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeClass()
    public void initializeData() {
        login = DataProvider.getInstance().getProperty("Login");
        passwd = DataProvider.getInstance().getProperty("Password");
        startUrl = DataProvider.getInstance().getProperty("StartUrl");

        driver.get(startUrl);
        //Not sure if it is right
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
    }

    @AfterTest()
    public void unloadState() throws InterruptedException {
        DataProvider.getInstance().closeProvider();
        driver.quit();
    }
}
