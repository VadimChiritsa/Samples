package by.epam.edu.autoframework.steps;

import by.epam.edu.autoframework.data.DataProvider;
import by.epam.edu.autoframework.drivercreator.FirefoxDriverCreator;
import by.epam.edu.autoframework.drivercreator.WebDriverCreator;
import by.epam.edu.autoframework.framework.LoginPage;
import by.epam.edu.autoframework.framework.mail.MainPage;
import junit.framework.Assert;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import org.junit.experimental.theories.DataPoint;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Natallia_Rakitskaya on 1/31/2017.
 */
public class MySteps{
    static WebDriver driver;
    static LoginPage loginPage;
    static MainPage mainPage;
    @When("I open google mail Home Page")
    public void iOpen(){
        WebDriverCreator creator = new FirefoxDriverCreator(DataProvider.getInstance()
                .getProperty("PathToFirefoxDriver"));
        driver = creator.factoryMethod();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        driver.get(DataProvider.getInstance().getProperty("StartUrl"));
    }

    @When("I enter login '$login' and password '$password'")
    public void iEnterCredentials(String login, String password){
        mainPage = loginPage.logInEmail(login, password);
    }

    @Then("Page title should contains '$login'")
    public void pageTitleShouldContains(String login){
        assertTrue(driver.getTitle().contains(login));
    }

    @When("I perform log out")
    public void iPerformLogout() {
        loginPage = mainPage.logOut();
    }

    @Then("Page title should not contains '$login'")
    public void iGetOnLoginPage(String login) {
        assertFalse(driver.getTitle().contains(login));
        driver.quit();
    }


}
