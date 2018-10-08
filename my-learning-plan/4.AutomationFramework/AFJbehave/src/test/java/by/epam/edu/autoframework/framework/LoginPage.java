package by.epam.edu.autoframework.framework;

import by.epam.edu.autoframework.framework.mail.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Aliaksandr_Sheliutsi on 1/18/2017.
 */
public class LoginPage extends AbstractPage {

    @FindBy(name = "Email")
    private WebElement emailField;

    @FindBy(name = "Passwd")
    private WebElement passwordField;

    @FindBy(name = "signIn")
    private WebElement signInButton;

    @FindBy(id = "edit-account-list")
    private WebElement editEmailList;

    @FindBy(id = "account-chooser-add-account")
    private WebElement addAccountButton;

    @FindBy(id = "account-chooser-link")
    private WebElement navigateToEmailListLink;

    @FindBy(id = "choose-account-0")
    private WebElement firstEmailInList;

    @FindBy(css = "li.GgHWyf")
    private WebElement noActiveEmailTextField;

    private MainPage mainPage;

    public LoginPage(WebDriver driver) {
        super(driver);
        mainPage = PageFactory.initElements(driver, MainPage.class);
    }

    public MainPage logInEmail(String logIn, String password) {
        emailField.sendKeys(logIn);
        emailField.submit();
        driver.waitForElementToBeClicable(passwordField);

        passwordField.sendKeys(password);
        passwordField.submit();

        mainPage.waitUntilPageIsLoaded();
        return mainPage;
    }

    public LoginPage waitForPageIsLoaded() {
        driver.waitForElementToBeClicable(navigateToEmailListLink);
        return this;
    }

    public LoginPage deleteEmailFromOptions() {
        navigateToEmailListLink.click();

        driver.waitForElementToBeClicable(editEmailList);
        editEmailList.click();

        driver.waitForInvisibilityOf(addAccountButton);
        firstEmailInList.click();
        driver.waitForElementToBeClicable(noActiveEmailTextField);

        editEmailList.click();
        return this;
    }
}
