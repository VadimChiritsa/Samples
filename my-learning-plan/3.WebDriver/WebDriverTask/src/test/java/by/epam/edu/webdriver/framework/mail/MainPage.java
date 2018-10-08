package by.epam.edu.webdriver.framework.mail;

import by.epam.edu.webdriver.data.Folder;
import by.epam.edu.webdriver.framework.AbstractPage;
import by.epam.edu.webdriver.framework.LoginPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Aliaksandr_Sheliutsi on 1/25/2017.
 */
public class MainPage extends AbstractPage {

    //=========Locators Area============================================================================================
    @FindBy(xpath = "//div[@gh='cm']")
    private WebElement createNewMailButton;

    //================
    @FindBys({ //div[gh='tm']//div[role='button'][act='10']
            @FindBy(xpath = "//div[@gh='tm']//div[@role='button']"),
            @FindBy(xpath = "//div[@gh='tm']//div[@act='10']")
    })
    private WebElement deleteSelectedEmailsButton;

    @FindBy(css = "form[enctype='multipart/form-data'] div.hl")
    private WebElement recipientComposingFieldOnlyClicable;

    @FindBy(css = "div.vh > span[role='link']")
    private WebElement messageAboutState;

    //================
    @FindBy(name = "q")
    private WebElement searchField;
    //================
    @FindBy(css = "img.Ha[src='images/cleardot.gif']")
    private WebElement closeEmail;

    @FindBy(xpath = "//div[contains(@aria-label, 'Ctrl-Enter')]")
    private WebElement sendButton;

    @FindBy(css = "span.aOy")
    private WebElement messageAboutSaving;

    @FindBy(xpath = "//form[@method='POST']//textarea[@role='combobox']")
    private WebElement recipientComposingField;

    @FindBy(name = "subjectbox")
    private WebElement subjectComposingField;

    @FindBy(css = "div.LW-avf")
    private WebElement contentComposingField;

    //================
    private String linkToOptionByXpath = "//a[contains(@href,'%s')]";

    private String selectedLinkOfFolderByCss = "div.aim.ain a[href*='%s']";

    private String emailAreaByTopicByXpath = "//div[@role='main']//tr[contains(string(),'%s')]";

    private String checkBoxByXpath = "//div[@role='checkbox']";

    //=========END of locators Area=====================================================================================

    public MainPage(WebDriver driver) {
        super(driver);
    }

    //===========Locator methods Area==========================================================================================

    private WebElement emailAreaByTopic(String topic) {
        return driver.findElement(By.xpath(String.format(emailAreaByTopicByXpath, topic)));
    }

    private WebElement emailCheckboxByTopic(String topic) {
        return driver.findElement(By.xpath(String.format(emailAreaByTopicByXpath + checkBoxByXpath, topic)));
    }

    private WebElement linkTo(String to) {
        return driver.findElement(By.xpath(String.format(linkToOptionByXpath, to)));
    }

    private WebElement activeLinkOfFolder(String folderName) {
        return driver.findElement(By.cssSelector(String.format(selectedLinkOfFolderByCss, folderName)));
    }

    //==================================================================================================================

    public MainPage selectEmailByTopic(String topic) {
        emailCheckboxByTopic(topic).click();
        return this;
    }

    public boolean checkEmailExistanceByTopic(String topic) {
        try {
            emailAreaByTopic(topic);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public MainPage openEmailByTopic(String topic) {
        emailAreaByTopic(topic).click();
        return this.waitUntillEmailIsOpened();
    }

    public MainPage deleteSelectedEmails() {
        deleteSelectedEmailsButton.click();
        driver.waitForVisibilityOf(messageAboutState);
        return this;
    }

    //====================
    public MainPage navigateToFolder(Folder folder) {
        this.linkTo(folder.getValue()).click();

        driver.waitForElementToBeClicable(activeLinkOfFolder(folder.getValue()));
        return this;
    }

    public MainPage createNewEmail() {
        createNewMailButton.click();
        return this;
    }

    //==========================
    public MainPage waitUntilPageIsLoaded() {
        driver.waitForElementToBeClicable(this.linkTo("SignOutOptions"));
        return this;
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public MainPage waitUntilMessageAboutStateAppears() {
        driver.waitForVisibilityOf(messageAboutState);
        return this;
    }

    public LoginPage logOut() {
        linkTo("SignOutOptions").click();
        linkTo("Logout").click();
        try {
            driver.waitForAlertIsPresent();
            Alert alert = driver.switchTo().alert();
            alert.accept();

        } catch (TimeoutException e) {
            //that's good if there is no alert!
        }
        return PageFactory.initElements(driver.getActualDriver(), LoginPage.class).waitForPageIsLoaded();
    }

    //=========================
    public MainPage closeEmailWithSaving() {
        //Class aOy present after saving email
        driver.waitForElementToBeClicable(messageAboutSaving);
        closeEmail.click();

        return this;
    }

    public MainPage sendEmail() {
        sendButton.click();
        return this.waitUntilMessageAboutStateAppears();
    }

    public MainPage fillEmail(String emailTo, String topic, String body) {
        driver.waitForVisibilityOf(recipientComposingField);
        recipientComposingField.sendKeys(emailTo);
        subjectComposingField.sendKeys(topic);
        contentComposingField.click();
        contentComposingField.sendKeys(body);

        return this;
    }

    public String getEmailBody() {
        return contentComposingField.getText();
    }

    public MainPage waitUntillEmailIsOpened() {
        driver.waitForVisibilityOf(contentComposingField);
        return this;
    }

}
