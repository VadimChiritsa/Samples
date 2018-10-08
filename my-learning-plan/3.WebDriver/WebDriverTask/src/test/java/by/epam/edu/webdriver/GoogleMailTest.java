package by.epam.edu.webdriver;

import by.epam.edu.webdriver.data.Folder;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Aliaksandr_Sheliutsi on 1/19/2017.
 */
public class GoogleMailTest extends AbstractMailTest {

    String emailTo;
    String emailTopic;
    String emailBody;

    @Test()
    public void LoginTest() {
        loginPage.logInEmail(login, passwd);
        Assert.assertTrue(driver.getTitle().contains(login));
    }

    @Test(dependsOnMethods = "LoginTest")
    public void javaScriptExcecutorTest() {
        mainPage.highLightElementAndMakeScreenShot(mainPage.getSearchField());
    }

    @Test(dependsOnMethods = "LoginTest")
    public void savingEmailToDraftsTest() {
        emailTo = login;
        emailTopic = "Topic:" + System.currentTimeMillis();
        emailBody = "Test body";

        mainPage.createNewEmail()
                .fillEmail(emailTo, emailTopic, emailBody)
                .closeEmailWithSaving()
                .navigateToFolder(Folder.Drafts);
        //Assert that emailTo and topic equal to near draft
        Assert.assertTrue(mainPage.checkEmailExistanceByTopic(emailTopic));
    }


    @Test(dependsOnMethods = "savingEmailToDraftsTest")
    public void draftBodyTest() {
        Assert.assertEquals(mainPage.openEmailByTopic(emailTopic).getEmailBody(),
                emailBody);
    }

    @Test(dependsOnMethods = "draftBodyTest")
    public void sendEmailFromDraftsTest() {
        mainPage.sendEmail();
        mainPage.navigateToFolder(Folder.Sent);
        mainPage.navigateToFolder(Folder.Drafts);
        Assert.assertFalse(mainPage.checkEmailExistanceByTopic(emailTopic));
    }


    @Test(dependsOnMethods = "sendEmailFromDraftsTest")
    public void savingSentEmailsTest() {
        mainPage.navigateToFolder(Folder.Sent);
        Assert.assertTrue(mainPage.checkEmailExistanceByTopic(emailTopic));
    }

    //Additional test cases:
    //1. Send a mail to us and check it's delivery
    //2. Deleting the email and check it's no more in the inbox folder
    //3. Checking that log out working correctly

    @Test(dependsOnMethods = "savingSentEmailsTest")
    public void emailArrivingTest() {
        mainPage.navigateToFolder(Folder.Inbox);
        Assert.assertTrue(mainPage.checkEmailExistanceByTopic(emailTopic));
    }

    @Test(dependsOnMethods = "emailArrivingTest")
    public void delitingEmailTest() {
        mainPage.selectEmailByTopic(emailTopic).deleteSelectedEmails();
        Assert.assertFalse(mainPage.checkEmailExistanceByTopic(emailTopic));
    }

    @Test(dependsOnMethods = "delitingEmailTest", alwaysRun = true)
    public void logOutFromAccountTest() {
        mainPage.logOut();
        Assert.assertFalse(driver.getTitle().contains(login));
    }
}
